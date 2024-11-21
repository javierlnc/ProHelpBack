package com.javierlnc.back_app.dto;


import lombok.Data;

@Data
public class TicketRequestDTO {
    private Long  categoryId;
    private String subject;
    private String description;

}
