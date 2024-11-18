package com.javierlnc.back_app.controller.tec;

import com.javierlnc.back_app.dto.TechnicianTicketCountDTO;
import com.javierlnc.back_app.entity.Ticket;
import com.javierlnc.back_app.entity.User;
import com.javierlnc.back_app.repository.TicketRepository;
import com.javierlnc.back_app.repository.UserRepository;
import com.javierlnc.back_app.service.panel.PanelService;
import com.javierlnc.back_app.service.ticket.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tec")
public class PanelController {
    @Autowired
    private PanelService panelService;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/technicians/ticket-count")
    public List<TechnicianTicketCountDTO> getTechniciansWithTicketCount( ) {
        return panelService.getTechniciansWithTicketCount();
    }
    @GetMapping("/technicians/ticket-count/{userId}")
    public TechnicianTicketCountDTO getAssignedTicketCountForUse (@PathVariable Long userId){
        User user = userRepository.findById(userId).orElseThrow();
        return panelService.getAssignedTicketCountForUse(user);
    }
    @GetMapping("/categories/ticket-count")
    public List<Object[]> getCategoriesWithTicketCount() {
        return panelService.getAllCategoriesWithTicketCount();
    }
    @GetMapping("/tickets/overdue")
    public ResponseEntity<List<Ticket>> getOverdueTickets(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<Ticket> overdueTickets = panelService.getOverdueTickets(user);
        return ResponseEntity.ok(overdueTickets);
    }
}
