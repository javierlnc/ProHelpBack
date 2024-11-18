package com.javierlnc.back_app.dto;


import lombok.Data;

@Data
public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private Long assignTicketId;
    private Long responsibleUserId;

}
