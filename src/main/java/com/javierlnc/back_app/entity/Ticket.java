package com.javierlnc.back_app.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.javierlnc.back_app.enums.ApprovalStatus;
import com.javierlnc.back_app.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;
    private String subject;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "priority_id")
    private Priority priority;
    private TicketStatus status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_technician_id")
    private User assignedTechnician;
    private LocalDateTime createdDate;
    private LocalDateTime dueDate;
    private ApprovalStatus approvalStatus;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "closed_by_id")
    private User closedBy;
    private String resolutionDescription;
    private LocalDateTime resolutionDate;
    @OneToMany(mappedBy = "assignTicket", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Task> tasks;


}
