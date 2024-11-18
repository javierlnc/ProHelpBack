package com.javierlnc.back_app.controller.admin;

import com.javierlnc.back_app.dto.AreaDTO;
import com.javierlnc.back_app.dto.AreaResponseDTO;
import com.javierlnc.back_app.service.area.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AreaController {
    private final AreaService areaService;

    @PostMapping("/area")
    public ResponseEntity<?> postArea (@RequestBody AreaDTO areaDTO){
        boolean success = areaService.createArea(areaDTO);
        if(success){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
    @GetMapping("/areas")
    public List<AreaDTO> getAllAreas() {
        return areaService.getAllAreas().getListAreaDTO();
    }
}
