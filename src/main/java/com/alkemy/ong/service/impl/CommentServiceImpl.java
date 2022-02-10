package com.alkemy.ong.service.impl;

import java.util.List;

import com.alkemy.ong.entity.Comment;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository repository;
    
    public List<Comment> findAllBody(){
        return repository.findAllBody();
    }
}
