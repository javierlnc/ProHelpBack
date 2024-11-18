package com.javierlnc.back_app.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReportDataDTO {
    private String title;             // Título del reporte (por ejemplo, "Reporte General")
    private LocalDateTime startDate;      // Fecha de inicio del rango del reporte
    private LocalDateTime endDate;        // Fecha de fin del rango del reporte
    private List<TicketDataDTO> tickets;
}
