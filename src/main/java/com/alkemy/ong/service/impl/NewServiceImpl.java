package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.entity.News;
import com.alkemy.ong.mapper.NewMapper;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewServiceImpl implements NewService {

  private static final int PAGE_SIZE = 10;

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
  public News getById(String id) {
    return newsRepository.getById(id);
  }

  @Override
  public boolean existsById(String id) {
    return newsRepository.existsById(id);
  }

  @Override
  public List<NewDTO> getAllByPage(Integer page) {
    Pageable paging = PageRequest.of(page, PAGE_SIZE);
    Page<News> pageNews = newsRepository.findAll(paging);
    List<News> news = pageNews.getContent();
    List<NewDTO> dtos = newMapper.entityList2newDTOList(news);
    return dtos;
  }


  @Override
  public void delete(String id) {
    newsRepository.deleteById(id);
  }
}
