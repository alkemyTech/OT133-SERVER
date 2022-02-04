package com.alkemy.ong.service;

import java.util.Optional;
import java.util.UUID;

import com.alkemy.ong.dto.CategoryDTO;

public interface CategoryService {
	
    CategoryDTO create(CategoryDTO categoryDTO);
    
    CategoryDTO findCategoryById(String id);
    
    CategoryDTO findById(String id);
    
    void delete(String id);
    
    Optional<CategoryDTO> updateCategory(CategoryDTO categoryDTO, UUID id);
}
