package com.javierlnc.back_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.javierlnc.back_app.enums.TasktStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    @JsonBackReference
    private Ticket assignTicket;
    private TasktStatus status;
    @ManyToOne
    @JoinColumn(name = "responsible_user_id", nullable = false)
    private User responsibleUser;
}
