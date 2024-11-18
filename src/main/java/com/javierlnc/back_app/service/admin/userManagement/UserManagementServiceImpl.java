package com.javierlnc.back_app.service.admin.userManagement;

import com.javierlnc.back_app.dto.UserDTO;
import com.javierlnc.back_app.dto.UserFilterDTO;
import com.javierlnc.back_app.dto.UserResponseDTO;
import com.javierlnc.back_app.entity.Area;
import com.javierlnc.back_app.entity.User;
import com.javierlnc.back_app.exception.EmailAlreadyExistsException;
import com.javierlnc.back_app.exception.UserNotFoundException;
import com.javierlnc.back_app.repository.AreaRepository;
import com.javierlnc.back_app.repository.UserRepository;
import com.javierlnc.back_app.service.area.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {
    private final UserRepository userRepository;
    private final AreaRepository areaRepository;
    private final AreaService areaService;

    public ResponseEntity<UserDTO> createUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("El email: " + userDTO.getEmail() + " ya se encuentra registrado");
        }
        User user = new User();
        mapUserFromDTO(userDTO, user);
        userRepository.save(user);
        return ResponseEntity.ok(user.getUserDTO());
    }
    public UserResponseDTO getAllUsers(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, 20);
        Page<User> userPage = userRepository.findAll(pageable);

        if (userPage.isEmpty()) {
            throw new UserNotFoundException("No se encontraron usuarios en la página " + pageNumber);
        }

        return buildUserResponseDTO(userPage);
    }
    public List<UserFilterDTO> getFilteredUsers(){
        List<String> roles = List.of("ADMIN", "TEC");
        List<User> users = userRepository.findByRoleIn(roles);
        return users.stream()
                .map(user -> new UserFilterDTO(user.getId(), user.getName(), user.getRole().name()))
                .collect(Collectors.toList());
    }


    public boolean updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario con ID: " + id + " no encontrado"));
        mapUserFromDTO(userDTO, user);
        userRepository.save(user);
        return true;
    }
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            throw new UserNotFoundException("Usuario con ID: " + id + " no encontrado");
        }
    }

    private User mapUserFromDTO(UserDTO userDTO, User user) {
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setArea(areaService.areaId(userDTO.getAreaId()));
        user.setRole(userDTO.getRole());
        if (isPasswordValid(userDTO.getPassword())) {
            user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        }
        return user;
    }
    private boolean isPasswordValid(String password) {
        return password != null && !password.isEmpty();
    }
    private UserResponseDTO buildUserResponseDTO(Page<User> userPage) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setPageNumber(userPage.getPageable().getPageNumber());
        userResponseDTO.setTotalPages(userPage.getTotalPages());
        userResponseDTO.setTotalElements((int) userPage.getTotalElements());
        userResponseDTO.setUserDTOList(userPage.getContent().stream()
                .map(User::getUserDTO)
                .collect(Collectors.toList()));
        return userResponseDTO;
    }

}
