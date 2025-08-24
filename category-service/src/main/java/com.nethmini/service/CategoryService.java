package com.nethmini.service;

import com.nethmini.dto.SalonDTO;
import com.nethmini.modal.Category;

import java.util.Set;

public interface CategoryService {
    Category saveCategory(Category category, SalonDTO salonDTO);
    Set<Category> getAllCategoriesBySalon(Long Id);
    Category getCategoryById(Long id) throws Exception;
    Category deleteCategoryById(Long id, Long salonId) throws Exception;
}
