package com.javierlnc.back_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TechnicianReportDTO {
    private Long userId;
    private String userName;
    private Long totalTickets; // Total de tickets
    private Long overdueTickets; // Tickets vencidos
    private Long onTimeTickets;
}
