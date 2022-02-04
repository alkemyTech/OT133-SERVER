package com.alkemy.ong.controller;

import java.util.*;

import com.alkemy.ong.service.SlideService;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            response.put("Slide deleted: " + id, HttpStatus.SC_OK);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            response.put("Exception: " + e.getLocalizedMessage(), HttpStatus.SC_CONFLICT);
            return ResponseEntity.ok(response);
        }
    }
}
