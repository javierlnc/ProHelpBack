package com.javierlnc.back_app.entity;

import com.javierlnc.back_app.dto.AreaDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public AreaDTO getAreaDTO() {
        AreaDTO areaDTO = new AreaDTO();
        areaDTO.setId(id);
        areaDTO.setName(name);
        return areaDTO;
    }
}
