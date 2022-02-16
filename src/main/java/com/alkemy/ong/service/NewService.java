package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.entity.News;
import com.alkemy.ong.exception.NewException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewService {
  NewDTO save(NewDTO dto) throws NewException ;

  News getById(String id);

  boolean existsById(String id);

  List<NewDTO> getAllByPage(Integer page);

  void delete(String id);
  
  NewDTO update(NewDTO dto, String id) throws NewException;

  void validateNewsForUpdateOrCreate(NewDTO newDTO) throws NewException;
}
