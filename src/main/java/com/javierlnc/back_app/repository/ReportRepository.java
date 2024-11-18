package com.javierlnc.back_app.repository;


import com.javierlnc.back_app.dto.ReportDataDTO;
import com.javierlnc.back_app.dto.TechnicianReportDTO;
import com.javierlnc.back_app.dto.TicketDataDTO;
import com.javierlnc.back_app.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("SELECT new com.javierlnc.back_app.dto.TicketDataDTO(t.id, t.subject, c.name, p.name, r.name, at.name, t.createdDate, t.dueDate, t.resolutionDate, " +
            "CASE WHEN t.resolutionDate > t.dueDate THEN true ELSE false END) " +
            "FROM Ticket t " +
            "JOIN t.category c " +
            "JOIN t.priority p " +
            "JOIN t.requester r " +
            "LEFT JOIN t.assignedTechnician at " +
            "WHERE t.createdDate BETWEEN :startDate AND :endDate " +
            "AND t.status = 5")
    List<TicketDataDTO> findClosedTicketsInRangeWithResolution(@Param("startDate") LocalDateTime startDate,
                                                               @Param("endDate") LocalDateTime endDate);

    @Query("SELECT new com.javierlnc.back_app.dto.TicketDataDTO(t.id, t.subject, c.name, p.name, r.name, at.name, t.createdDate, t.dueDate, t.resolutionDate, " +
            "CASE WHEN t.resolutionDate > t.dueDate THEN true ELSE false END) " +
            "FROM Ticket t " +
            "JOIN t.category c " +
            "JOIN t.priority p " +
            "JOIN t.requester r " +
            "LEFT JOIN t.assignedTechnician at " +
            "WHERE t.createdDate BETWEEN :startDate AND :endDate " +
            "AND t.status = 5 " +
            "AND t.assignedTechnician.id = :technicianId")
    List<TicketDataDTO> findClosedTicketsInRangeWithAssignedTechnician(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("technicianId") Long technicianId);
    @Query("SELECT new com.javierlnc.back_app.dto.TechnicianReportDTO(u.id, u.name, " +
            "COUNT(t) AS totalTickets, " +
            "SUM(CASE WHEN t.resolutionDate > t.dueDate THEN 1 ELSE 0 END) AS overdueTickets, " +
            "SUM(CASE WHEN t.resolutionDate <= t.dueDate THEN 1 ELSE 0 END) AS onTimeTickets) " +
            "FROM User u " +
            "LEFT JOIN Ticket t ON u.id = t.assignedTechnician.id " +
            "WHERE u.role IN (0, 1) " +
            "AND (t.createdDate BETWEEN :startDate AND :endDate OR t.id IS NULL) " +
            "AND (t.status = 5 OR t.id IS NULL) " +
            "GROUP BY u.id, u.name")
    List<TechnicianReportDTO>findClosedTicketsInRangeWithResolutionByUsers(@Param("startDate") LocalDateTime startDate,
                                                                           @Param("endDate") LocalDateTime endDate);
}
