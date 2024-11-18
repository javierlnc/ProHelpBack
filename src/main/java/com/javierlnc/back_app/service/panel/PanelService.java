package com.javierlnc.back_app.service.panel;

import com.javierlnc.back_app.dto.TechnicianTicketCountDTO;
import com.javierlnc.back_app.entity.Ticket;
import com.javierlnc.back_app.entity.User;

import java.util.List;

public interface PanelService {
    List<TechnicianTicketCountDTO> getTechniciansWithTicketCount();
    TechnicianTicketCountDTO getAssignedTicketCountForUse(User user);
    List<Object[]> getAllCategoriesWithTicketCount();
    List<Ticket>getOverdueTickets(User user);

}
