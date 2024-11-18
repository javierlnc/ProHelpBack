package com.javierlnc.back_app.dto;

import com.javierlnc.back_app.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TechnicianTicketCountDTO {
    private Long userId;
    private String userName;
    private Long ticketCount;


    public TechnicianTicketCountDTO(User user, Long aLong) {
        this.userId = user.getId();
        this.userName = user.getUsername();
        this.ticketCount = aLong;
    }
}
