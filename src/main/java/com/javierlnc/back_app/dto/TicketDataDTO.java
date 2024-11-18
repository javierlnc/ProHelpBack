package com.javierlnc.back_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class TicketDataDTO {
    private Long id;
    private String subject;
    private String categoryName;
    private String priorityName;
    private String requesterName;
    private String assignedTechnicianName;
    private LocalDateTime createdDate;
    private LocalDateTime dueDate;
    private LocalDateTime resolutionDate;
    private boolean isOverdue;
}
