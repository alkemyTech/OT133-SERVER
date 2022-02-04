package com.alkemy.ong.service;

import com.alkemy.ong.entity.Slide;

public interface SlideService {
    
    Slide findById(String id);

    void deleteSlide(String id) throws Exception;
}
