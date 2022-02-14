package com.alkemy.ong.service;

import java.util.*;

import com.alkemy.ong.dto.CommentBodyDTO;
import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.entity.Comment;
import com.alkemy.ong.exception.CommentException;

public interface CommentService {
    
    Optional<CommentDTO> create(CommentDTO commentDTO);

    CommentDTO findById(String id);

    void delete(String id);
    
    List<CommentDTO> getAllComments(String id) throws CommentException;

    CommentDTO updateComment(CommentDTO comment, String id);

    Integer validateUser(String commentId);

    List<CommentBodyDTO> bringCommentsBodies();
}
