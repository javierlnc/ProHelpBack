package com.javierlnc.back_app.service.admin.resolution;

import com.javierlnc.back_app.dto.ResolutionDTO;
import com.javierlnc.back_app.dto.ResolutionResponseDTO;
import com.javierlnc.back_app.entity.Resolution;
import com.javierlnc.back_app.repository.ResolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResolutionServiceImpl implements ResolutionService {
    private final ResolutionRepository resolutionRepository;

    @Transactional
    public boolean createResolution(ResolutionDTO resolutionDTO) {
            try{
                Resolution  resolution = new Resolution();
                mapResolutionFromDTO(resolutionDTO, resolution);
                resolutionRepository.save(resolution);
                return true;
            } catch (Exception e) {
                throw new RuntimeException("Error al crear la resolución: " + e.getMessage(), e);
            }
        }
        public ResolutionResponseDTO getAllResolution(int pageNumber){
            Pageable pageable = PageRequest.of(pageNumber, 4);
            Page<Resolution> resolutions = resolutionRepository.findAll(pageable);

            ResolutionResponseDTO resolutionResponseDTO = new ResolutionResponseDTO();
            resolutionResponseDTO.setPageNumber(resolutions.getPageable().getPageNumber());
            resolutionResponseDTO.setTotalPages(resolutions.getTotalPages());
            resolutionResponseDTO.setResolutionDTOList(resolutions.stream().map(Resolution :: getResolutionDTO).collect(Collectors.toList()));
            return resolutionResponseDTO;
        }
        @Transactional
        public boolean UpdateResolution(Long id, ResolutionDTO resolutionDTO){
        Resolution resolution = resolutionRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Reslución no encontrada"));
       mapResolutionFromDTO(resolutionDTO, resolution);
        resolutionRepository.save(resolution);
        return true;
        }
        @Transactional
        public boolean deleteResolution(Long id){
        if (resolutionRepository.existsById(id)){
            resolutionRepository.deleteById(id);
            return true;
        }else {
            throw new RuntimeException("Resolucion con id "+id+" no se encuentra");
        }
        }
        public ResolutionResponseDTO getAllResolutionList (){

            List<Resolution> resolutions = resolutionRepository.findAll();
            ResolutionResponseDTO resolutionResponseDTO = new ResolutionResponseDTO();
            resolutionResponseDTO.setResolutionDTOList(resolutions.stream().map(Resolution::getResolutionDTO).collect(Collectors.toList()));
            return resolutionResponseDTO;
        }
        private Resolution mapResolutionFromDTO (ResolutionDTO resolutionDTO, Resolution resolution){
        resolution.setName(resolutionDTO.getName());
        resolution.setDescription(resolutionDTO.getDescription());
        return  resolution;
        }
    }

