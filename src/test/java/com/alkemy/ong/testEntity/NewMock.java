package com.alkemy.ong.testEntity;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.entity.Category;


public class NewMock {



  public static Category createCategoryMock() {

    Category category = new Category();
    category.setId("123");
    category.setName("nee");
    //savedCategory = categoryRepository.save(category);
    return category;
  }

  public static NewDTO createNewMock() {

    NewDTO newDTO = new NewDTO();
    newDTO.setId("12");
    newDTO.setImage("image");
    newDTO.setName("A valid name");
    newDTO.setContent("A valid content");
    //newDTO.setCategoryId(savedCategory.getId());
    return newDTO;
  }

}
