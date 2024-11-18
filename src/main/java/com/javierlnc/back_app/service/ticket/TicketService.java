package com.javierlnc.back_app.service.ticket;

import com.javierlnc.back_app.dto.ApprovalResponseDTO;
import com.javierlnc.back_app.dto.TicketRequestDTO;
import com.javierlnc.back_app.dto.TicketResponseDTO;
import com.javierlnc.back_app.entity.Ticket;
import com.javierlnc.back_app.entity.User;

import java.util.List;

public interface TicketService {
    boolean createTicket(TicketRequestDTO ticketDTO);
    Ticket assignTechnicianAndPriority(Long ticketId, Long technicianId, Long priorityId);
    List<Ticket> getTicketByUserRole(User user);
    TicketResponseDTO getTicketById (Long Id);
    Ticket closeTicket(Long ticketId, String resolutionText, User user);
    List<ApprovalResponseDTO> getListTicketPendingApproval(User user);
    ApprovalResponseDTO getTicketPendingApproval(Long ticketId);
    boolean approveOrRefuseClosure(Long ticketId, Long status);


}
