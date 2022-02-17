package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.entity.Category;
import com.alkemy.ong.entity.News;
import com.alkemy.ong.exception.ActivityException;
import com.alkemy.ong.exception.NewException;
import com.alkemy.ong.mapper.NewMapper;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NewServiceImpl implements NewService {

  private static final int PAGE_SIZE = 10;

  @Autowired
  NewMapper newMapper;

  @Autowired
  NewsRepository newsRepository;

  @Autowired
  private CategoryRepository categoryRepository;
  
  @Override
  public NewDTO save(NewDTO dto)throws NewException {
    validateNewsForUpdateOrCreate(dto);
    
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


  @Override
  public NewDTO update(NewDTO dto, String id) throws NewException {
    
    Optional<News> news = newsRepository.findById(id);

    if (news.isPresent()) {
      
      validateNewsForUpdateOrCreate(dto);
      
      news.get().setName(dto.getName());
      news.get().setImage(dto.getImage());
      news.get().setContent(dto.getContent());

      NewDTO newDTO = newMapper.entity2newDTO(news.get());

      newsRepository.save(news.get());

      return newDTO;
    } else {
      throw new NewException("The New id does not exist in the database or is incorrect.");
    }

  }

  @Override
  public void validateNewsForUpdateOrCreate(NewDTO newDTO) throws NewException {
    
    if (newDTO.getCategoryId() == null||newDTO.getCategoryId().isEmpty() ||newDTO.getCategoryId().equals(" ")) {
      throw new NewException("Category null or empty.");
    }
    
    Optional<Category> category = categoryRepository.findById(newDTO.getCategoryId());
    
    if (newDTO.getName() == null ||newDTO.getName().isEmpty() ||newDTO.getName().equals(" ")) {
      throw new NewException("Name null or empty.");
    }
    if (newDTO.getContent() == null||newDTO.getContent().isEmpty() ||newDTO.getContent().equals(" ")) {
      throw new NewException("Content nulo or empty.");
    }
    if (newDTO.getImage() == null||newDTO.getImage().isEmpty() ||newDTO.getImage().equals(" ")) {
      throw new NewException("Image null or empty.");
    }
    if ( !category.isPresent()) {
      throw new NewException("Category does not exist in the database.");
    }
  }
  
  
}
