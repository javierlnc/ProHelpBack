package com.javierlnc.back_app.repository;

import com.javierlnc.back_app.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository <Category,Long> {
    Optional<Category> findByName(String name);
    Optional<Category> findById(Long id);
    @Query("SELECT c.id AS categoryId, c.name AS categoryName, COUNT(t.id) AS ticketCount " +
            "FROM Category c LEFT JOIN Ticket t ON t.category.id = c.id " +
            "GROUP BY c.id, c.name")
    List<Object[]> findAllCategoriesWithTicketCount();
}
