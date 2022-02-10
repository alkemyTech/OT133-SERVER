package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.entity.News;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class NewMapper {
  public News newDTO2Entity(NewDTO dto) {
    News news = new News();
    news.setName(dto.getName());
    news.setContent(dto.getContent());
    news.setImage(dto.getImage());
    news.setCategoryId(dto.getCategoryId());
    return news;
  }

  public NewDTO entity2newDTO(News news) {
    NewDTO dto = new NewDTO();
    dto.setId(news.getId());
    dto.setName(news.getName());
    dto.setContent(news.getContent());
    dto.setImage(news.getImage());
    dto.setCategoryId(news.getCategoryId());
    dto.setTimestamps(news.getTimestamps());
    return dto;
  }
  
  public List<NewDTO> entityList2newDTOList (List<News> newsList){
    List<NewDTO> dtos = new ArrayList<>();
    for (News news: newsList ) {
      dtos.add(this.entity2newDTO(news));
    }
    return dtos;
  }
}
