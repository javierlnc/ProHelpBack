package com.javierlnc.back_app.controller;

import com.javierlnc.back_app.dto.TaskDTO;
import com.javierlnc.back_app.dto.TaskResponseDTO;
import com.javierlnc.back_app.entity.User;
import com.javierlnc.back_app.repository.UserRepository;
import com.javierlnc.back_app.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createTask (@RequestBody TaskDTO taskDTO){
        boolean success = taskService.createTask(taskDTO);
        if (success){
            return  ResponseEntity.status(HttpStatus.CREATED).build();
        }else{
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<TaskResponseDTO>> getTaskForUser(Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        try{
            List<TaskResponseDTO> tasks = taskService.getTaskForUser(user);
            return ResponseEntity.ok(tasks);
        }catch (Exception e) {
            throw new RuntimeException("Problemas en el controlador");
        }
    }
    @PutMapping("{taskId}/close")
    public ResponseEntity<?> closeTask(@PathVariable Long taskId){
        boolean success = taskService.closeTask(taskId);
        if (success){
            return  ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }else{
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
