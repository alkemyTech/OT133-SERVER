package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.entity.News;

public interface NewService {
	
    NewDTO save(NewDTO dto);

    boolean existsById(String id);
    
    News getById(String id);
    
    void delete(String id);
}
