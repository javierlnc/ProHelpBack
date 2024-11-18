package com.javierlnc.back_app.dto;

import lombok.Data;

@Data
public class TaskResponseDTO {
    private Long id;
    private String name;
    private  String description;
    private String status;
    private String responsibleUserName;
    private String assignTicketSubject;

}
