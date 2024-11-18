package com.javierlnc.back_app.service.area;

import com.javierlnc.back_app.dto.AreaDTO;
import com.javierlnc.back_app.dto.AreaResponseDTO;
import com.javierlnc.back_app.entity.Area;


public interface AreaService {
    boolean createArea(AreaDTO areaDTO);
    AreaResponseDTO getAllAreas ();
    Area areaId (Long id);

}
