package com.javierlnc.back_app.repository;


import com.javierlnc.back_app.entity.Ticket;
import com.javierlnc.back_app.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByRequesterIdOrAssignedTechnicianId(Long requesterId, Long technicianId);
    List<Ticket> findByRequesterId(Long requesterId);
    List<Ticket> findAllByStatus(TicketStatus ticketStatus);
    List<Ticket> findAllByStatusAndRequesterId(TicketStatus status, Long requesterId);
    @Query("SELECT t FROM Ticket t WHERE t.dueDate < CURRENT_DATE AND t.status = 1")
    List<Ticket> findOverdueTickets();
    @Query("SELECT t FROM Ticket t WHERE t.dueDate < CURRENT_DATE AND t.status = 1 AND t.assignedTechnician.id = :userId")
    List<Ticket> findOverdueTicketsByUser(@Param("userId") Long userID);


}
