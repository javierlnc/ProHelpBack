package com.javierlnc.back_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriorityDTO {
    private String name;
    private String description;
    private Integer responseTime;

}
