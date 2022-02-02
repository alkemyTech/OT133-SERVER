package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDTO;

public interface CategoryService {
	
    CategoryDTO findCategoryById(String id);
    
    CategoryDTO findById(String id);
    
    void delete(String id);
}
