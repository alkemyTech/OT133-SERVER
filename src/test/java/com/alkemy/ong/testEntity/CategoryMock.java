package com.alkemy.ong.testEntity;

import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.entity.Category;

public class CategoryMock {
  
    public static Category createCategoryMock() {

    Category category = new Category();
   //category.setId("123");
    category.setName("nee");
    category.setDescription("description");
    //savedCategory = categoryRepository.save(category);
    return category;
  }

  public static CategoryDTO createCategoryDTOMock() {

    CategoryDTO categoryDTO = new CategoryDTO();
    //categoryDTO.setId("12");
    categoryDTO.setImage("image");
    categoryDTO.setName("A valid name");
    categoryDTO.setDescription("A valid description");
    //categoryDTO.setCategoryId(savedCategory.getId());
    return categoryDTO;
  }
}
