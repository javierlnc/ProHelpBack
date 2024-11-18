package com.javierlnc.back_app.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryResponseDTO {
    private List<CategoryDTO> categoryDTOList;
    private Integer totalPages;
    private Integer pageNumber;
}
