package com.javierlnc.back_app.service.admin.userManagement;

import com.javierlnc.back_app.dto.UserDTO;
import com.javierlnc.back_app.dto.UserFilterDTO;
import com.javierlnc.back_app.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserManagementService {
    ResponseEntity<UserDTO> createUser(UserDTO userDTO);
    UserResponseDTO getAllUsers(int pageNumber);
    List<UserFilterDTO> getFilteredUsers();
    boolean updateUser(Long id, UserDTO userDTO);
    boolean deleteUser(Long id);
}
