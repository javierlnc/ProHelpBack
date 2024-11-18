package com.javierlnc.back_app.repository;

import com.javierlnc.back_app.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByResponsibleUserId(Long responsibleUserId);
}
