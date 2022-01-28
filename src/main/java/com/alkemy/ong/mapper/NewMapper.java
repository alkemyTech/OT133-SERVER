package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.News;
import com.alkemy.ong.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NewMapper {
    public News newDTO2Entity (NewDTO dto) {
        News news = new News();
        news.setName(dto.getName());
        news.setContent(dto.getContent());
        news.setImage(dto.getImage());
        news.setCategoryId(dto.getCategoryId());
        news.setTimestamps(LocalDateTime.now());
        return news;
    }

    public NewDTO entity2newDTO (News news) {
        NewDTO dto = new NewDTO();
        dto.setId(news.getId());
        dto.setName(news.getName());
        dto.setContent(news.getContent());
        dto.setImage(news.getImage());
        dto.setCategoryId(news.getCategoryId());
        dto.setTimestamps(LocalDateTime.now());
        return dto;
    }
}
