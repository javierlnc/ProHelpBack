package com.javierlnc.back_app.dto;

import com.javierlnc.back_app.entity.Category;
import com.javierlnc.back_app.entity.User;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TicketRequestDTO {
    private Long  categoryId;
    private String subject;
    private String description;

}
