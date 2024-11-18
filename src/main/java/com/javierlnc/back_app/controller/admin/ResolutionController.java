package com.javierlnc.back_app.controller.admin;

import com.javierlnc.back_app.dto.ResolutionDTO;
import com.javierlnc.back_app.service.admin.resolution.ResolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ResolutionController {
    private final ResolutionService resolutionService;

    @PostMapping("/resolution")
    public ResponseEntity<?> createResolution(@RequestBody ResolutionDTO resolutionDTO) {
        boolean success = resolutionService.createResolution(resolutionDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }
    }

    @GetMapping("/resolution/{pageNumber}")
    public ResponseEntity<?> getAllResolution(@PathVariable int pageNumber) {
        return ResponseEntity.ok(resolutionService.getAllResolution(pageNumber));
    }

    @GetMapping("/resolution/all")
    public List<ResolutionDTO> getAllResolutionList() {
        return resolutionService.getAllResolutionList().getResolutionDTOList();
    }

    @PutMapping("/resolution/{id}")
    public ResponseEntity<?> updateResolution(@PathVariable Long id, @RequestBody ResolutionDTO resolutionDTO) {
        try {
            boolean isUpdate = resolutionService.UpdateResolution(id, resolutionDTO);
            if (isUpdate) {
                return ResponseEntity.ok(Collections.singletonMap("message", "Resolución Actualizada"));
            } else {
                return ResponseEntity.badRequest().body("Error al actualizar resolucion");

            }
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/resolution/{id}")
    public ResponseEntity<?> deleteResolution(@PathVariable Long id) {
        try {
            boolean isDelete = resolutionService.deleteResolution(id);

            if (isDelete) {
                return ResponseEntity.ok(Collections.singletonMap("message", "La resolucion ha sido elminida"));
            } else {
                return ResponseEntity.badRequest().body("Error al eliminar la resolucion");
            }
        } catch (Exception e) {
            return  ResponseEntity.status(404).body(e.getMessage());
        }

    }
}
