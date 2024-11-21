package com.javierlnc.back_app.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketResponseDTO {
    private Long id;
    private String subject;
    private String description;
    private String requesterName;
    private String status;
    private LocalDateTime createdDate;
    private Long priorityId;
    private String category;
    private LocalDateTime dueDate;
    private Long assignedTechnicianId;
    }
