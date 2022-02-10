package com.alkemy.ong.service;

import com.alkemy.ong.dto.CommentDTO;

public interface CommentService {

    CommentDTO findById(String id);

    void delete(String id);
}
