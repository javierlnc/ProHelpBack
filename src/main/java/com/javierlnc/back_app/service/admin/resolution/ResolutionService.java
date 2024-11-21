package com.javierlnc.back_app.service.admin.resolution;


import com.javierlnc.back_app.dto.ResolutionDTO;
import com.javierlnc.back_app.dto.ResolutionResponseDTO;

public interface ResolutionService {
   boolean createResolution (ResolutionDTO resolutionDTODTO);
    ResolutionResponseDTO getAllResolution(int pageNumber);
    boolean UpdateResolution(Long id, ResolutionDTO resolutionDTO);
    boolean deleteResolution(Long id);
    ResolutionResponseDTO getAllResolutionList ();
}
