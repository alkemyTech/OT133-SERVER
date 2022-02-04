package com.alkemy.ong.service;

import java.util.Optional;
import java.util.UUID;

import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.exception.CategoryException;

public interface CategoryService {
	
    CategoryDTO create(CategoryDTO categoryDTO) throws Exception;
    
    public void verifyCategory(CategoryDTO category) throws CategoryException;
            
    CategoryDTO findCategoryById(String id);
    
    CategoryDTO findById(String id);
    
    void delete(String id);
    
    Optional<CategoryDTO> updateCategory(CategoryDTO categoryDTO, UUID id);
}
