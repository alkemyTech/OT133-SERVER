package com.alkemy.ong.mapper;

import org.springframework.stereotype.Component;

import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.entity.Slide;

@Component
public class SlideMapper {

	public SlideDTO toSlideDTO(Slide slide) {
		return new SlideDTO(slide.getText(),slide.getOrderNumber(),slide.getTimestamps(),slide.getImageUrl(),slide.getOrganization());
	}
	
	
	public Slide toSlide(SlideDTO slideDTO,Slide slide) {
		slide.setText(slideDTO.getText());
		slide.setOrderNumber(slideDTO.getOrderNumber());
		return slide;
	}
	
}
