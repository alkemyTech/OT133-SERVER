package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDTOList;
import com.alkemy.ong.service.Category;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    
    @Autowired
    Category categoryService;

    @Autowired
    public CategoryController(Category categoryService) {
        this.categoryService = categoryService;
    }
    
    @GetMapping()
    public List<CategoryDTOList> getAllName(){
        return categoryService.getAllName();
    }
    
}
