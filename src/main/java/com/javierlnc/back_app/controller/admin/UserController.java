package com.javierlnc.back_app.controller.admin;

import com.javierlnc.back_app.dto.UserDTO;
import com.javierlnc.back_app.dto.UserFilterDTO;
import com.javierlnc.back_app.enums.UserRole;
import com.javierlnc.back_app.exception.UserNotFoundException;
import com.javierlnc.back_app.service.admin.userManagement.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor

public class UserController {
    private  final UserManagementService userManagementService;
    @PostMapping("/user")
    public ResponseEntity<?> creteUser(@RequestBody UserDTO userDTO){
        ResponseEntity success = userManagementService.createUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/users/{pageNumber}")
    public ResponseEntity<?> getAllUsers(@PathVariable int pageNumber){
        return ResponseEntity.ok(userManagementService.getAllUsers(pageNumber));
    }
    @GetMapping("/users/filtered")
    public ResponseEntity<List<UserFilterDTO>> getFilteredUsers() {
        List<UserFilterDTO> users = userManagementService.getFilteredUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            boolean isUpdated = userManagementService.updateUser(id, userDTO);
            if (isUpdated) {
                return ResponseEntity.ok(Collections.singletonMap("message", "Usuario actualizado con éxito"));

            } else {
                return ResponseEntity.badRequest().body("Error al actualizar el usuario");
            }
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    @DeleteMapping("users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            boolean isDeleted = userManagementService.deleteUser(id);
            if (isDeleted) {
                return ResponseEntity.ok(Collections.singletonMap("message","Usuario eliminado con éxito"));
            } else {
                return ResponseEntity.badRequest().body("Error al eliminar el usuario");
            }
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
