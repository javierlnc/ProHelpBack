package com.javierlnc.back_app.repository;

import com.javierlnc.back_app.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PriorityRepository extends JpaRepository<Priority,Long> {

    Optional<Priority> findByName(String name);
}
