package com.javierlnc.back_app.service.area;

import com.javierlnc.back_app.dto.AreaDTO;
import com.javierlnc.back_app.dto.AreaResponseDTO;
import com.javierlnc.back_app.entity.Area;
import com.javierlnc.back_app.repository.AreaRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements  AreaService {
    private final AreaRepository areaRepository;

    public boolean createArea(AreaDTO areaDTO){
        if (areaRepository.findByName(areaDTO.getName()).isPresent()){
            throw new EntityExistsException("El area: " + areaDTO.getName() + " ya se encuentra registrado");
        }
        try{
            Area area = new Area();
            area.setName(areaDTO.getName());
            areaRepository.save(area);
            return true;

        } catch (Exception e) {
            return  false;
        }
    }
    public AreaResponseDTO getAllAreas (){
        List<Area> areas = areaRepository.findAll();  // Trae todas las áreas sin paginación
        AreaResponseDTO areaResponseDTO = new AreaResponseDTO();
        areaResponseDTO.setListAreaDTO(areas.stream().map(Area::getAreaDTO).collect(Collectors.toList()));  // Mapear a DTO
        return areaResponseDTO;
    }
    public Area areaId (Long id){
        Optional <Area> find = areaRepository.findById(id);
        Area area = new Area();
        area.setId(find.get().getId());
        area.setName(find.get().getName());
        return area;
    }
}
