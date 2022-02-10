package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.entity.Comment;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;
    
    @Autowired
    CommentMapper commentMapper;

    @Override
    public CommentDTO findById(String id) {
        Comment comment = commentRepository.findById(id).get();
        return commentMapper.toDTO(comment);
    }

    @Override
    public void delete(String id) {
        Comment comment = commentRepository.findById(id).get();
        commentRepository.delete(comment);
    }
}
