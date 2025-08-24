package com.nethmini.service.impl;

import com.nethmini.dto.SalonDTO;
import com.nethmini.modal.Category;
import com.nethmini.repository.CategoryRepository;
import com.nethmini.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category, SalonDTO salonDTO) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setImage(category.getImage());
        newCategory.setSalonId(salonDTO.getId());

        return categoryRepository.save(newCategory);
    }

    @Override
    public Set<Category> getAllCategoriesBySalon(Long Id) {
        return categoryRepository.findBySalonId(Id);
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            throw new Exception("category not exist with id"+id);
        }
        return category;
    }

    @Override
    public Category deleteCategoryById(Long id, Long salonId) throws Exception {
        Category category = getCategoryById(id);
        if (!category.getSalonId().equals(salonId)) {
            throw new Exception("You don't have permission to delete this category");        }
        categoryRepository.deleteById(id);
        return category;
    }
}
