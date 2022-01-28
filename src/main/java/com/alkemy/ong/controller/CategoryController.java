package com.alkemy.ong.controller;

import com.alkemy.ong.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (categoryService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
