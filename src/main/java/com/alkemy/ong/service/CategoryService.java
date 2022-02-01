package com.alkemy.ong.service;

import java.util.Optional;
import java.util.UUID;

import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.dto.CategoryDTOList;
import java.util.List;

public interface CategoryService {
    CategoryDTO findCategoryById(String id);
    
    Optional<CategoryDTO> updateCategory(CategoryDTO categoryDTO, UUID id);
    
    public List<CategoryDTOList> getAllName();
}
