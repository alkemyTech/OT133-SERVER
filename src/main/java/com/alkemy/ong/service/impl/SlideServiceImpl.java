package com.alkemy.ong.service.impl;

import java.util.Optional;

import com.alkemy.ong.entity.Slide;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.SlideService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlideServiceImpl implements SlideService {
    
    @Autowired
    private SlideRepository slideRepository;

    public void deleteSlide(String id) throws Exception{
        Optional<Slide> slideOptional = slideRepository.findById(id);
        if(slideOptional.isPresent()){
            Slide slide = slideOptional.get();
            slideRepository.delete(slide);
        } else throw new Exception("The specified slide does not exist");
    }
}
