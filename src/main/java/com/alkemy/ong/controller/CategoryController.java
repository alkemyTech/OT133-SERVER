package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.service.CategoryService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PreAuthorize("ROL_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (categoryService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryDetails(@PathVariable String id) {
        CategoryDTO categoryDetails = categoryService.findCategoryById(id);
        if (categoryDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(categoryDetails);
    }
    
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
    										BindingResult result, 
    										@PathVariable UUID id){
    	
    	Map<String, Object> response = new HashMap<>();
    	Map<String, Object> validations = new HashMap<>();
    	
    	if(result.hasErrors()) {
			result.getFieldErrors().forEach(error -> validations.put(error.getField(), error.getDefaultMessage()));
			response.put("message", validations);
			return ResponseEntity.badRequest().body(response);
		}
    	
    	Optional<CategoryDTO> optCategoryDTO = this.categoryService.updateCategory(categoryDTO, id);

    	if(!optCategoryDTO.isPresent()) {
    		response.put("Error", String.format("Category with ID %s not found.", id));
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    	}else {
    		response.put("ok", optCategoryDTO);
    		return ResponseEntity.ok(response);
    	}
    }

}
