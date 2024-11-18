package com.javierlnc.back_app.service.priority;

import com.javierlnc.back_app.dto.PriorityDTO;
import com.javierlnc.back_app.entity.Priority;
import com.javierlnc.back_app.repository.PriorityRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriorityService {
    private final PriorityRepository priorityRepository;

    @PostConstruct
    public void CreateDefaultPriorities() {
        List<PriorityDTO> defaultPriorities = List.of(
                new PriorityDTO("1. Urgente", "requiere atención inmediata.",2),
                new PriorityDTO("2. Alta","rápidamente", 8),
                new PriorityDTO("3. Media","Iresolverse en un plazo razonable" ,23),
                new PriorityDTO("4. Baja", "Incidencia menor que puede ser resuelta en un plazo flexible", 48)
        );
        for (PriorityDTO priorityDTO: defaultPriorities){
            Optional<Priority> priorityExisting = priorityRepository.findByName(priorityDTO.getName());
            if (priorityExisting.isEmpty()){
                Priority priority = new Priority();
                priority.setName(priorityDTO.getName());
                priority.setDescription(priorityDTO.getDescription());
                priority.setResponseTime(priorityDTO.getResponseTime());
                priorityRepository.save(priority);
            }

        }


    }
}
