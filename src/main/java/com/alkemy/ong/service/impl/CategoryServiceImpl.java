package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.entity.Category;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryDTO findCategoryById(String id){
        try {
            Category foundCategory = categoryRepository.findById(id).get();
            return categoryMapper.categoryEntity2DTO(foundCategory);
        }catch (Exception e) {
            System.out.println("No encontrado");
        }
        return null;

    }
    
    @Override
    public Optional<CategoryDTO> updateCategory(CategoryDTO categoryDTO, UUID id) {
    	Optional<Category> optCategory = this.categoryRepository.findById(id.toString());
    	
    	if(!optCategory.isPresent()) {
    		return Optional.empty();
    	}else {
    		Category categoryUpdate = this.categoryMapper.categoryDTO2Entity(categoryDTO,optCategory.get());
    		
    		return Optional.of(this.categoryMapper.categoryEntity2DTO(this.categoryRepository.save(categoryUpdate)));
    	}
    }
}
