package com.javierlnc.back_app.dto;

import com.javierlnc.back_app.entity.Area;
import com.javierlnc.back_app.enums.UserRole;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String name;
    private Area area;
    private Long areaId;
    private UserRole role;

}
