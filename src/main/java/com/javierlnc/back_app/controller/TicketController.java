package com.javierlnc.back_app.controller;

import com.javierlnc.back_app.dto.ApprovalResponseDTO;
import com.javierlnc.back_app.dto.TechnicianPriorityDTO;
import com.javierlnc.back_app.dto.TicketRequestDTO;
import com.javierlnc.back_app.dto.TicketResponseDTO;
import com.javierlnc.back_app.entity.Ticket;
import com.javierlnc.back_app.entity.User;
import com.javierlnc.back_app.enums.UserRole;
import com.javierlnc.back_app.exception.UserNotFoundException;
import com.javierlnc.back_app.repository.UserRepository;
import com.javierlnc.back_app.service.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createTicket(@RequestBody TicketRequestDTO ticketDTO){
        boolean success = ticketService.createTicket(ticketDTO);
        if (success){
            return  ResponseEntity.status(HttpStatus.CREATED).build();
        }else{
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }
    }
    @PutMapping("/{ticketId}/assign")
    public ResponseEntity<Ticket> assignTechnicianAndPriority(
            @PathVariable Long ticketId,
            @RequestBody TechnicianPriorityDTO technicianPriorityDTO) {
        try {
            Ticket updatedTicket = ticketService.assignTechnicianAndPriority(ticketId, technicianPriorityDTO.getTechnicianId(), technicianPriorityDTO.getPriorityId());
            return ResponseEntity.ok(updatedTicket);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Ticket>> getTicketsForUser(Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        try{
            List<Ticket> tickets = ticketService.getTicketByUserRole(user);
            return  ResponseEntity.ok(tickets);

        } catch (Exception e) {
            throw new RuntimeException("Problemas en el controlador");
        }
    }
    @GetMapping("/approval")
    public ResponseEntity<List<ApprovalResponseDTO>> getListTicketPendingApproval (Authentication authentication){
        try{
            User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            List<ApprovalResponseDTO> tickets = ticketService.getListTicketPendingApproval(user);
            return ResponseEntity.ok(tickets);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @GetMapping("/approval/{ticketId}")
    public ResponseEntity<ApprovalResponseDTO> getTicketPendingApproval(@PathVariable Long ticketId){
        try{
            ApprovalResponseDTO approvalResponseDTO = ticketService.getTicketPendingApproval(ticketId);
            return ResponseEntity.ok(approvalResponseDTO);
        } catch (RuntimeException e) {
            throw new RuntimeException("Problemas en el contralador");
        }
    }
    @PutMapping("/{ticketId}/close")
    public ResponseEntity<Ticket> closeTicket(@PathVariable Long ticketId, Authentication authentication,@RequestBody String resolutionDescription ){
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        try{
            Ticket ticket = ticketService.closeTicket(ticketId,resolutionDescription,user);
            return ResponseEntity.ok(ticket);
        } catch (Exception e) {
            throw new RuntimeException("Problemas en el controlador");
        }
    }
    @PutMapping("/{ticketId}/approve-close")
    public ResponseEntity<?> approveOrRefuseClose(
            @PathVariable Long ticketId,
            @RequestBody Long status,
            Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        if (user.getRole() == UserRole.ADMIN) {
            ticketService.approveOrRefuseClosure(ticketId, status);
            return ResponseEntity.noContent().build(); // Usamos noContent para respuestas exitosas sin cuerpo
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Permiso denegado: solo los administradores pueden aprobar o rechazar cierres de tickets.");
        }
    }
    @GetMapping("/{Id}")
    public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable Long Id){
        TicketResponseDTO ticket = ticketService.getTicketById(Id);
        return  ResponseEntity.ok(ticket);
    }

}
