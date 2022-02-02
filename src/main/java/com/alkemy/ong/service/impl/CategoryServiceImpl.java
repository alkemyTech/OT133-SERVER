package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.entity.Category;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public CategoryDTO findById(String id) {
		Category category = categoryRepository.findById(id).get();
		return categoryMapper.categoryEntity2DTO(category);
	}

	@Override
	public void delete(String id) {
		Category category = categoryRepository.findById(id).get();
		categoryRepository.delete(category);
	}
}
