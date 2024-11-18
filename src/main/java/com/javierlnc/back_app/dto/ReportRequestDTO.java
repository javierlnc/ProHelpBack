package com.javierlnc.back_app.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReportRequestDTO {
    private String reportType;
    private LocalDateTime starDate;
    private LocalDateTime endDate;
    private Long assignedTechnicianId;
}
