package com.javierlnc.back_app.controller.admin;


import com.javierlnc.back_app.dto.CategoryDTO;
import com.javierlnc.back_app.exception.UserNotFoundException;
import com.javierlnc.back_app.service.admin.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping("/category")
    public ResponseEntity<?> createRoom(@RequestBody CategoryDTO categoryDTO){
        boolean success = categoryService.createCategory(categoryDTO);
        if (success){
            return  ResponseEntity.status(HttpStatus.CREATED).build();
        }else{
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }
    }

    @GetMapping("/categories/{pageNumber}")
    public ResponseEntity<?> getAllCategories(@PathVariable int pageNumber){
        return ResponseEntity.ok(categoryService.getAllCategories(pageNumber));

    }
    @GetMapping("/category/all")
    public List<CategoryDTO> getAllCategoriesList() {
        return categoryService.getAllCategoriesList().getCategoryDTOList();
    }
    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory (@PathVariable Long id,@RequestBody CategoryDTO categoryDTO){
        try{
            boolean isUpdated = categoryService.UpdateCategory(id, categoryDTO);
            if (isUpdated) {
                return ResponseEntity.ok(Collections.singletonMap("message", "Usuario actualizado con éxito"));

            } else {
                return ResponseEntity.badRequest().body("Error al actualizar el usuario");
            }
        } catch (UserNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
    }
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategorie (@PathVariable Long id){
        try{
            boolean isDeleted = categoryService.deleteCategory(id);
            if (isDeleted){
                return ResponseEntity.ok(Collections.singletonMap("message","Usuario eliminado con éxito"));
            }else{
                return ResponseEntity.badRequest().body("Error al eliminar el usuario");
            }
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }
}
