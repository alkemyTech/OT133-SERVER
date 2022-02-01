package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.entity.News;
import com.alkemy.ong.mapper.NewMapper;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.NewService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewServiceImpl implements NewService {

  @Autowired
  NewMapper newMapper;

  @Autowired
  NewsRepository newsRepository;

  public NewDTO save(NewDTO dto) {
    News news = newMapper.newDTO2Entity(dto);
    News newSave = newsRepository.save(news);
    NewDTO result = newMapper.entity2newDTO(newSave);
    return result;

  }

	@Override
	public boolean existsById(String id) {
		return newsRepository.existsById(id);
	}

	@Override
	public News getById(String id) {
		return newsRepository.getById(id);
	}

	@Override
	public void delete(String id) {
		newsRepository.deleteById(id);
	}
	
}
