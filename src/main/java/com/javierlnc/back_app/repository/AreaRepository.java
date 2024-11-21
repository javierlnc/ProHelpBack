package com.javierlnc.back_app.repository;


import com.javierlnc.back_app.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
    Optional<Area> findByName(String name);

}
