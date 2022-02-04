package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.entity.Slide;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.SlideService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlideServiceImpl implements SlideService {
    
    @Autowired
    private SlideRepository slideRepository;
    
    @Autowired
    private SlideMapper slideMapper;

	@Override
	public Optional<Slide> getbyId(String id) {
		Optional<Slide> optSlide = this.slideRepository.findById(id);
		if(!optSlide.isPresent()) {
			return Optional.empty();
		}
		return optSlide;
	}

	@Override
	public Optional<SlideDTO> update(SlideDTO slideDTO, String id) {
		Optional<Slide> optSlide = this.slideRepository.findById(id);
		
		if(!optSlide.isPresent()) {
			return Optional.empty();
		}
		Slide slideSave = this.slideMapper.toSlide(slideDTO, optSlide.get());
		return Optional.of(this.slideMapper.toSlideDTO(this.slideRepository.save(slideSave)));
		
	}
}
