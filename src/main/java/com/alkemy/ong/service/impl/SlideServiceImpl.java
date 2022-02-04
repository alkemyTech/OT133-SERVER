package com.alkemy.ong.service.impl;

import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.SlideService;

import org.springframework.beans.factory.annotation.Autowired;

public class SlideServiceImpl implements SlideService {
    
    @Autowired
    private SlideRepository slideRepository;
}
