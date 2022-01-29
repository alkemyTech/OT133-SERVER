package com.alkemy.ong.mapper;

import java.time.LocalDateTime;
import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.entity.News;
import org.springframework.stereotype.Component;

@Component
public class NewMapper {
  public News newDTO2Entity(NewDTO dto) {
    News news = new News();
    news.setName(dto.getName());
    news.setContent(dto.getContent());
    news.setImage(dto.getImage());
    news.setTimestamps(LocalDateTime.now());
    return news;
  }

  public NewDTO entity2newDTO(News news) {
    NewDTO dto = new NewDTO();
    dto.setId(news.getId());
    dto.setName(news.getName());
    dto.setContent(news.getContent());
    dto.setImage(news.getImage());
    dto.setCategoryId(news.getCategory().getId());
    dto.setTimestamps(LocalDateTime.now());
    return dto;
  }
}
