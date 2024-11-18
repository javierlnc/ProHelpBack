package com.javierlnc.back_app.service.admin.category;

import com.javierlnc.back_app.dto.CategoryDTO;
import com.javierlnc.back_app.dto.CategoryResponseDTO;
import com.javierlnc.back_app.entity.Category;

public interface CategoryService {
    boolean createCategory (CategoryDTO categoryDTO);
    CategoryResponseDTO getAllCategories(int pageNumber);
    boolean UpdateCategory(Long id, CategoryDTO categoryDTO);
    boolean deleteCategory(Long id);
    CategoryResponseDTO getAllCategoriesList ();
    Category categoryId (Long id);
}
