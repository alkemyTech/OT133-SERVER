package com.alkemy.ong.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.exception.CategoryException;

public interface CategoryService {
	
    CategoryDTO create(CategoryDTO categoryDTO) throws Exception;
    
    public void verifyCategory(CategoryDTO category) throws CategoryException;
            
    CategoryDTO findCategoryById(String id);
    
    CategoryDTO findById(String id);
    
    void delete(String id);
    
    Optional<CategoryDTO> updateCategory(CategoryDTO categoryDTO, UUID id);
    
    List<CategoryDTO> findAllPage(Pageable pageable);
}
