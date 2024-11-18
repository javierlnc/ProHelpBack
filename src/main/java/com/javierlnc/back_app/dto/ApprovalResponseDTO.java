package com.javierlnc.back_app.dto;

import com.javierlnc.back_app.enums.TicketStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApprovalResponseDTO {
    private Long id;
    private String subject;
    private String description;
    private String requesterName;
    private String status;
    private LocalDateTime createdDate;
    private String priorityName;
    private String category;
    private LocalDateTime dueDate;
    private String assignedTechnicianName;
    private LocalDateTime resolutionDate;
    private String resolutionDescription;
    private String closeByName;

}
