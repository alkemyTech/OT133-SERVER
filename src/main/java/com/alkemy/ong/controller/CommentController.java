package com.alkemy.ong.controller;

import com.alkemy.ong.service.CommentService;
import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.entity.Comment;
import com.alkemy.ong.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController
@RequestMapping("/comments")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;
    
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (commentService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        commentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<Object> getAll(){
        try {
            List<Comment> comments = StreamSupport.stream(commentService.findAllBody().spliterator(), false).
            collect(Collectors.toList());
            return ResponseEntity.ok(comments);
        } catch (Exception e){
            Map<String, Object> response = new HashMap<>();
            response.put("Exception: " + e.getLocalizedMessage(), HttpStatus.CONFLICT);
            return ResponseEntity.badRequest().body(response);
        }
        
    }
    
    
    @PreAuthorize("hasAuthority('ROL_USER')")
    @PostMapping
	  public ResponseEntity<?> create(@Validated @RequestBody CommentDTO commentDTO) {
		Map<String, Object> response = new HashMap<>();
		Optional<CommentDTO> commentSaved = this.commentService.create(commentDTO);

		if (!commentSaved.isPresent()) {
			response.put("Bad request", "userId or newId invalid.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(commentSaved);
	}

}
