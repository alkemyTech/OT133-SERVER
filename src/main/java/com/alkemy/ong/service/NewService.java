package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.entity.News;

public interface NewService {
    NewDTO save(NewDTO dto);

	News getById(String id);
	
	boolean existsById(String id);
}
