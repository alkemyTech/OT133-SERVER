package com.alkemy.ong.controller;



import com.alkemy.ong.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/comments")
public class CommentController extends BaseController {

    @Autowired
    private CommentService conmmentService;
    
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (conmmentService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        conmmentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
