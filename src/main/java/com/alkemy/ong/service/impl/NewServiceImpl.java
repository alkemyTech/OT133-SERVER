package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.News;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.mapper.NewMapper;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewServiceImpl implements NewService {

    @Autowired
    NewMapper newMapper;

    @Autowired
    NewsRepository newsRepository;

    public NewDTO save(NewDTO dto){
        News news = newMapper.newDTO2Entity(dto);
        News newSave = newsRepository.save(news);
        NewDTO result = newMapper.entity2newDTO(newSave);
        return result;

    }
}
