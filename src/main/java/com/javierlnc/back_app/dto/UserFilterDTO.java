package com.javierlnc.back_app.dto;

import lombok.Data;

@Data
public class UserFilterDTO {
    private Long id;
    private String name;
    private String role;
    public UserFilterDTO(Long id, String name,  String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}

