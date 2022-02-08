package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.service.SlideService;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/slides")
public class SlideController {
    
    @Autowired
    private SlideService slideService;


    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteSlide(@PathVariable String id){
        Map<String, Object> response = new HashMap<>();
        try{
            slideService.deleteSlide(id);
            response.put("Slide deleted: " + id, HttpStatus.OK);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            response.put("Exception: " + e.getLocalizedMessage(), HttpStatus.CONFLICT);
            return ResponseEntity.ok(response);
        }
    }

    
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSlide(@Validated @RequestBody SlideDTO slideDTO, @PathVariable UUID id){
    	Map<String, Object> response = new HashMap<>();
    	
    	Optional<SlideDTO> slideUpdateDTO = this.slideService.update(slideDTO,id.toString());
    	
    	if(!slideUpdateDTO.isPresent()) {
    		response.put("Error", String.format("Slide with ID %s not found.", id));
			return new ResponseEntity<>(response,org.springframework.http.HttpStatus.NOT_FOUND);
    	}
    	response.put("ok", slideUpdateDTO.get());
		return ResponseEntity.ok(response);
    }
    
    @PostMapping
    public ResponseEntity<?> createNewSlide(@RequestBody SlideDTO request) throws Exception {
  	  try {
  		    SlideDTO response = slideService.saveSlide(request);
  		    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  	} catch (Exception e) {
  			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getLocalizedMessage());
  	}

    }
}
