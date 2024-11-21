package com.javierlnc.back_app.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TechnicianTicketCountDTO {
    private Long userId;
    private String userName;
    private Long ticketCount;
}
