package com.javierlnc.back_app.service.task;

import com.javierlnc.back_app.dto.TaskDTO;
import com.javierlnc.back_app.dto.TaskResponseDTO;
import com.javierlnc.back_app.entity.User;

import java.util.List;

public interface TaskService  {
    boolean createTask(TaskDTO taskDTO);
    List<TaskResponseDTO> getTaskForUser(User user);
    boolean closeTask(Long taskId);
}
