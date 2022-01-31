package com.alkemy.ong.service;

import java.util.Optional;

import com.alkemy.ong.dto.NewDTO;

public interface NewService {
    NewDTO save(NewDTO dto);
    
    Optional<NewDTO> getById(String id);
}
