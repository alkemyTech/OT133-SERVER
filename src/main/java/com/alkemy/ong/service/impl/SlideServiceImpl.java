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

    public Slide findById(String id){
        Optional<Slide> slide = slideRepository.findById(id);
		if(slide.isPresent())
            return slide.get();
        return null;
    }

    public void deleteSlide(String id) throws Exception{
        Slide slide = this.findById(id);
        if(slide != null){
            slideRepository.delete(slide);
        } else throw new Exception("The specified slide does not exist");
    }
}
