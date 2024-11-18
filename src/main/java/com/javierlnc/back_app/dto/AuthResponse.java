package com.javierlnc.back_app.dto;

import com.javierlnc.back_app.enums.UserRole;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private  Long userId;
    private UserRole Rol;
    private String username;

}
