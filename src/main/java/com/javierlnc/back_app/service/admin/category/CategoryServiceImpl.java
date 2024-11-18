package com.javierlnc.back_app.service.admin.category;

import com.javierlnc.back_app.dto.CategoryDTO;
import com.javierlnc.back_app.dto.CategoryResponseDTO;
import com.javierlnc.back_app.entity.Area;
import com.javierlnc.back_app.entity.Category;
import com.javierlnc.back_app.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public boolean createCategory (CategoryDTO categoryDTO){
        if (categoryRepository.findByName(categoryDTO.getName()).isPresent()){
            throw new RuntimeException("La categoría: " + categoryDTO.getName() + " ya se encuentra registrada");
        }
        try{
            Category category = new Category();
            category.setName(categoryDTO.getName());
            category.setDescription(categoryDTO.getDescription());
            categoryRepository.save(category);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public CategoryResponseDTO getAllCategories(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber,10);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setPageNumber(categoryPage.getPageable().getPageNumber());
        categoryResponseDTO.setTotalPages(categoryPage.getTotalPages());
        categoryResponseDTO.setCategoryDTOList(categoryPage.stream().map(Category :: getCategoryDTO).collect(Collectors.toList()));
        return categoryResponseDTO;
    }
    public boolean UpdateCategory(Long id, CategoryDTO categoryDTO){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Categoria No encontrada"));
       mapCategoryFromDTO(categoryDTO, category);
        categoryRepository.save(category);
        return true;
    }
    public boolean deleteCategory(Long id){
        if (categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
            return true;
        }else {
            throw  new RuntimeException("Categoria con id "+ id +"no existe");
        }
    }
    public CategoryResponseDTO getAllCategoriesList (){
        List <Category> categories =  categoryRepository.findAll();
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setCategoryDTOList(categories.stream().map(Category :: getCategoryDTO).collect(Collectors.toList()));
        return categoryResponseDTO;
    }
    public Category categoryId (Long id){
        Optional<Category> find = categoryRepository.findById(id);
        Category category = new Category();
        category.setId(find.get().getId());
        category.setName(find.get().getName());
        return category;
    }

    private Category mapCategoryFromDTO(CategoryDTO categoryDTO, Category category){
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return category;

    }
}
