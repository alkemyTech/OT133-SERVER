package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.entity.Slide;
import com.alkemy.ong.service.SlideService;
import com.amazonaws.services.apigateway.model.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    	}
    	response.put("ok", slideUpdateDTO.get());
		return ResponseEntity.ok(response);
    }
    
    @GetMapping()
    public ResponseEntity<List<Slide>> findAllDefined(){
    	try {
	    	List<Slide> listImage = StreamSupport
	    			.stream(slideService.findAllDefined().spliterator(), false)
	    			.collect(Collectors.toList());
	    	return new ResponseEntity(listImage, HttpStatus.OK);
		} catch (BadRequestException ex) {
			return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}

    }
}
