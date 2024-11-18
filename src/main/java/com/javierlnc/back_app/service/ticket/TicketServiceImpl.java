package com.javierlnc.back_app.service.ticket;

import com.javierlnc.back_app.dto.ApprovalResponseDTO;
import com.javierlnc.back_app.dto.TicketRequestDTO;
import com.javierlnc.back_app.dto.TicketResponseDTO;
import com.javierlnc.back_app.entity.Category;
import com.javierlnc.back_app.entity.Priority;
import com.javierlnc.back_app.entity.Ticket;
import com.javierlnc.back_app.entity.User;
import com.javierlnc.back_app.enums.ApprovalStatus;
import com.javierlnc.back_app.enums.TicketStatus;
import com.javierlnc.back_app.enums.UserRole;
import com.javierlnc.back_app.exception.TechnicianNotFoundException;
import com.javierlnc.back_app.repository.PriorityRepository;
import com.javierlnc.back_app.repository.TicketRepository;
import com.javierlnc.back_app.repository.UserRepository;
import com.javierlnc.back_app.service.admin.category.CategoryService;
import com.javierlnc.back_app.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{
    private final AuthService authService;
    private  final TicketRepository ticketRepository;
    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private final PriorityRepository priorityRepository;


    public boolean createTicket(TicketRequestDTO ticketDTO){
        try{
            Ticket ticket = new Ticket();
            Category category = categoryService.categoryId(ticketDTO.getCategoryId());
            ticket.setCategory(category);
            ticket.setSubject(ticketDTO.getSubject());
            ticket.setDescription(ticketDTO.getDescription());
            ticket.setRequester(authService.getAuthenticatedUser());
            ticket.setCreatedDate(LocalDateTime.now());
            ticket.setStatus(TicketStatus.NEW);
            ticketRepository.save(ticket);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            throw  new RuntimeException("No se pudo crear ticket, por ");
        }
    }
    public  Ticket assignTechnicianAndPriority(Long ticketId, Long technicianId, Long priorityId){
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
        User technician = userRepository.findById(technicianId).orElseThrow(() -> new TechnicianNotFoundException("Tecnico no encontrado"));
        Priority priority = priorityRepository.findById(priorityId)
                .orElseThrow(() -> new RuntimeException("Prioridad no encontrada"));
        ticket.setPriority(priority);
        ticket.setAssignedTechnician(technician);
        ticket.setDueDate(calculateDueDate(ticket.getCreatedDate(), priority));
        ticket.setStatus(TicketStatus.OPEN);

        return ticketRepository.save(ticket);

    }


    public List<Ticket> getTicketByUserRole(User user){
        UserRole role = user.getRole();
        List<Ticket> tickets = switch (role) {
            case ADMIN -> ticketRepository.findAll();
            case TEC -> ticketRepository.findByRequesterIdOrAssignedTechnicianId(user.getId(), user.getId());
            case GEN -> ticketRepository.findByRequesterId(user.getId());
            default -> throw new IllegalArgumentException("Rol de usuario no válido");
        };
        return tickets;
    }
    public List<ApprovalResponseDTO> getListTicketPendingApproval(User user) {
        List<Ticket> tickets = (user.getRole() == UserRole.ADMIN)
                ? ticketRepository.findAllByStatus(TicketStatus.PENDING_APPROVAL)
                : ticketRepository.findAllByStatusAndRequesterId(TicketStatus.PENDING_APPROVAL, user.getId());

        return tickets.stream()
                .map(this::convertToApprovalResponseDTO)
                .collect(Collectors.toList());
    }
    public ApprovalResponseDTO getTicketPendingApproval(Long ticketId){
        Ticket ticket =ticketRepository.findById(ticketId).orElseThrow(()->new RuntimeException("Ticket no encontrado"));
        if (ticket.getStatus() == TicketStatus.PENDING_APPROVAL){
            ApprovalResponseDTO approvalResponseDTO =  convertToApprovalResponseDTO(ticket);

            return  approvalResponseDTO;
        }else{
            throw new RuntimeException("Ticket no se puede autorizar");
        }
    }

    public TicketResponseDTO getTicketById (Long id){
        return ticketRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }
    private ApprovalResponseDTO convertToApprovalResponseDTO(Ticket ticket) {
        ApprovalResponseDTO dto = new ApprovalResponseDTO();
        dto.setId(ticket.getId());
        dto.setSubject(ticket.getSubject());
        dto.setDescription(ticket.getDescription());
        dto.setRequesterName(ticket.getRequester().getName());
        dto.setStatus(ticket.getStatus().name());
        dto.setCreatedDate(ticket.getCreatedDate());
        dto.setPriorityName(ticket.getPriority().getName());
        dto.setCategory(ticket.getCategory().getName());
        dto.setDueDate(ticket.getDueDate());
        dto.setAssignedTechnicianName(Optional.ofNullable(ticket.getAssignedTechnician()).map(User::getName).orElse(null));
        dto.setResolutionDate(ticket.getResolutionDate());
        dto.setResolutionDescription(ticket.getResolutionDescription());
        dto.setCloseByName(ticket.getClosedBy().getName());
        return dto;
    }
    public boolean approveOrRefuseClosure(Long ticketId, Long status) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));

        // Verificar si el ticket está pendiente de aprobación
        if (ticket.getStatus() != TicketStatus.PENDING_APPROVAL) {
            return false;
        }
        switch (status.intValue()) {
            case 1: // Aprobar cierre
                ticket.setApprovalStatus(ApprovalStatus.APPROVED);
                ticket.setStatus(TicketStatus.RESOLVED);
                ticketRepository.save(ticket);
                return true;

            case 2: // Rechazar cierre
                ticket.setApprovalStatus(ApprovalStatus.REFUSED);
                ticket.setStatus(TicketStatus.OPEN);
                ticketRepository.save(ticket);
                return true;

            default:
                throw new IllegalArgumentException("Estado de aprobación no válido.");
        }
    }

    private TicketResponseDTO convertToDTO(Ticket ticket) {
        TicketResponseDTO ticketDTO = new TicketResponseDTO();
        ticketDTO.setId(ticket.getId());
        ticketDTO.setSubject(ticket.getSubject());
        ticketDTO.setDescription(ticket.getDescription());
        ticketDTO.setRequesterName(ticket.getRequester().getName());
        ticketDTO.setStatus(ticket.getStatus().name());
        ticketDTO.setCreatedDate(ticket.getCreatedDate());
        ticketDTO.setPriorityId(ticket.getPriority()!= null ? ticket.getPriority().getId() : null);
        ticketDTO.setCategory(ticket.getCategory().getName());
        ticketDTO.setDueDate(ticket.getDueDate() != null ? ticket.getDueDate() : null);
        ticketDTO.setAssignedTechnicianId(ticket.getAssignedTechnician() != null ? ticket.getAssignedTechnician().getId():null);
        return ticketDTO;
    }
    public Ticket closeTicket(Long ticketId, String resolutionText, User user){
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        ticket.setStatus(TicketStatus.PENDING_APPROVAL);
        ticket.setResolutionDescription(resolutionText);
        ticket.setResolutionDate(LocalDateTime.now());
        ticket.setClosedBy(user);
        return ticketRepository.save(ticket);
    }
    private LocalDateTime calculateDueDate(LocalDateTime createdDate, Priority priority) {
        return createdDate.plusHours(priority.getResponseTime());
    }


}
