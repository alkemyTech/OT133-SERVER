package com.alkemy.ong.controller;

import com.alkemy.ong.entity.Comment;
import com.alkemy.ong.service.CommentService;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/comments")
public class CommentController {
    
    @Autowired
    private CommentService commentService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<Object> getAll(){
        try {
            List<Comment> comments = StreamSupport.stream(commentService.findAllBody().spliterator(), false).
            collect(Collectors.toList());
            return ResponseEntity.ok(comments);
        } catch (Exception e){
            Map<String, Object> response = new HashMap<>();
            response.put("Exception: " + e.getLocalizedMessage(), HttpStatus.SC_CONFLICT);
            return ResponseEntity.badRequest().body(response);
        }
        
    }
}
