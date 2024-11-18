package com.javierlnc.back_app.repository;

import com.javierlnc.back_app.dto.TechnicianTicketCountDTO;
import com.javierlnc.back_app.entity.User;
import com.javierlnc.back_app.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByRole(UserRole role);
    List<User> findByRoleIn(List<String> roles);
    Optional<User> findByEmail(String email);
    @Query("SELECT new com.javierlnc.back_app.dto.TechnicianTicketCountDTO(u.id, u.name, COUNT(t)) " +
            "FROM User u " +
            "JOIN Ticket t ON u.id = t.assignedTechnician.id " +
            "WHERE u.role IN (0, 1) " +
            "GROUP BY u.id, u.name")
    List<TechnicianTicketCountDTO> findTechniciansWithTicketCount();
    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.assignedTechnician.id = :userId")
    Long countTicketsByUserId(@Param("userId") Long userId);
}
