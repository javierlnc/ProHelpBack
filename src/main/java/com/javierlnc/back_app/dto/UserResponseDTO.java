package com.javierlnc.back_app.dto;

import lombok.Data;

import java.util.List;
@Data
public class UserResponseDTO {
    private List<UserDTO> userDTOList;
    private Integer totalPages;
    private Integer totalElements;
    private  Integer pageNumber;
}
