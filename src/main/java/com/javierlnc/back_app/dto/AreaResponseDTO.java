package com.javierlnc.back_app.dto;

import com.javierlnc.back_app.entity.Area;
import lombok.Data;

import java.util.List;

@Data
public class AreaResponseDTO {
    private List<AreaDTO> listAreaDTO;
}
