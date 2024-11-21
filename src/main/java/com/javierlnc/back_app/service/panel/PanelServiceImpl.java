package com.javierlnc.back_app.service.panel;

import com.javierlnc.back_app.dto.TechnicianTicketCountDTO;
import com.javierlnc.back_app.entity.Ticket;
import com.javierlnc.back_app.entity.User;
import com.javierlnc.back_app.enums.UserRole;
import com.javierlnc.back_app.repository.CategoryRepository;
import com.javierlnc.back_app.repository.TicketRepository;
import com.javierlnc.back_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PanelServiceImpl implements PanelService{
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TicketRepository ticketRepository;

    public List<TechnicianTicketCountDTO> getTechniciansWithTicketCount(){
        return userRepository.findTechniciansWithTicketCount();
    }
    public TechnicianTicketCountDTO getAssignedTicketCountForUse(User user){
        return new TechnicianTicketCountDTO(user.getId(),user.getUsername(),userRepository.countTicketsByUserId(user.getId()));

    }
    public List<Object[]> getAllCategoriesWithTicketCount() {
        return categoryRepository.findAllCategoriesWithTicketCount();
    }
    public List<Ticket>getOverdueTickets(User user){
        if (user.getRole() == UserRole.ADMIN){
            return ticketRepository.findOverdueTickets();
        }else{
            return ticketRepository.findOverdueTicketsByUser(user.getId());
        }

    }
}
