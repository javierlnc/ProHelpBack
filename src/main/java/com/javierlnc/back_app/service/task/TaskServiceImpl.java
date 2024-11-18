package com.javierlnc.back_app.service.task;

import com.javierlnc.back_app.dto.TaskDTO;
import com.javierlnc.back_app.dto.TaskResponseDTO;
import com.javierlnc.back_app.entity.Task;
import com.javierlnc.back_app.entity.User;
import com.javierlnc.back_app.enums.TasktStatus;
import com.javierlnc.back_app.exception.UserNotFoundException;
import com.javierlnc.back_app.repository.TaskRepository;
import com.javierlnc.back_app.repository.TicketRepository;
import com.javierlnc.back_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private  final TaskRepository taskRepository;
    public boolean createTask(TaskDTO taskDTO){
       try{
           Task task = new Task();
           task.setName(taskDTO.getName());
           task.setDescription(taskDTO.getDescription());
           task.setAssignTicket(ticketRepository.findById(taskDTO.getAssignTicketId()).orElseThrow(()->new RuntimeException("solicitud no encontrada")));
           task.setResponsibleUser(userRepository.findById(taskDTO.getResponsibleUserId()).orElseThrow(()-> new UserNotFoundException("Usuario No encontrado")));
           task.setStatus(TasktStatus.OPEN);
           taskRepository.save(task);
           return true;
       } catch (RuntimeException e) {
           throw new RuntimeException("Error al crear la tarea");
       }
    }
    public List<TaskResponseDTO> getTaskForUser(User user){
        List<Task> task = taskRepository.findByResponsibleUserId(user.getId());
        return task.stream()
                .map(this::convertToTaskDTO)
                .collect(Collectors.toList());
    }
    public boolean closeTask(Long taskId){
        try{
            Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("No se encontra la tarea"));
            task.setStatus(TasktStatus.CLOSE);
            taskRepository.save(task);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Se produjo un error al cerrar la tarea");
        }

    }
    public TaskResponseDTO convertToTaskDTO(Task task){
        TaskResponseDTO dto=  new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus().name());
        dto.setResponsibleUserName(task.getResponsibleUser().getName());
        dto.setAssignTicketSubject(task.getAssignTicket().getSubject());
        return  dto;
    }
}
