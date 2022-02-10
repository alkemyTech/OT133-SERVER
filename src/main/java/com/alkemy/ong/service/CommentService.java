package com.alkemy.ong.service;

import java.util.*;

import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.entity.Comment;

public interface CommentService {

    List<Comment> findAllBody();
    
    Optional<CommentDTO> create(CommentDTO commentDTO);

}
