package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewService {
    NewDTO save(NewDTO dto);

	News getById(String id);
	
	boolean existsById(String id);

  List<NewDTO> getAllByPage(Integer page);
}
