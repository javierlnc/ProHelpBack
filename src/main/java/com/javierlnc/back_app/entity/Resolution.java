package com.javierlnc.back_app.entity;

import com.javierlnc.back_app.dto.ResolutionDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Resolution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    public ResolutionDTO getResolutionDTO() {
        ResolutionDTO resolutionDTO = new ResolutionDTO();
        resolutionDTO.setId(id);
        resolutionDTO.setName(name);
        resolutionDTO.setDescription(description);

        return resolutionDTO;
    }
}
