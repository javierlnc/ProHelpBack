package com.javierlnc.back_app.dto;

import lombok.Data;
import java.util.List;
@Data
public class ResolutionResponseDTO {
    private List<ResolutionDTO> resolutionDTOList;
    private Integer totalPages;
    private Integer pageNumber;
}
