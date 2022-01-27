package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.entity.Category;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    public void delete(String id){
        categoryRepository.deleteById(id);
    }

    public CategoryDTO findById(String id){
        Optional<Category> request = categoryRepository.findById(id);
        if (request.isPresent()){
            Category category = request.get();
            CategoryDTO dto = categoryMapper.entity2categoryDTO(category);
            return dto;
        }
        return null;
    }

}
