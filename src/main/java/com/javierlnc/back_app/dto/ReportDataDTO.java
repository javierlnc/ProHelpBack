package com.javierlnc.back_app.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReportDataDTO {
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<TicketDataDTO> tickets;
}
