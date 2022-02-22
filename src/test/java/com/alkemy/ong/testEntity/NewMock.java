package com.alkemy.ong.testEntity;

import org.springframework.beans.factory.annotation.Autowired;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.entity.Category;
import com.alkemy.ong.entity.News;
import com.alkemy.ong.repository.CategoryRepository;


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
