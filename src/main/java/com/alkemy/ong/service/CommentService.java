package com.alkemy.ong.service;

import java.util.*;

import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.entity.Comment;
import com.alkemy.ong.exception.CommentException;

public interface CommentService {

    List<Comment> findAllBody();
    
    Optional<CommentDTO> create(CommentDTO commentDTO);

    CommentDTO findById(String id);

    void delete(String id);
    
    List<CommentDTO> getAllComments(String id) throws CommentException;
}
