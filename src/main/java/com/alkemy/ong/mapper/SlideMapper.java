package com.alkemy.ong.mapper;


import com.alkemy.ong.dto.SlidePublicDTO;
import org.springframework.stereotype.Component;

import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.entity.Slide;

import java.util.ArrayList;
import java.util.List;

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
  
  public SlidePublicDTO slidetoSlidePublicDTO(Slide slide) {

    SlidePublicDTO slideDTO=new SlidePublicDTO();
    slideDTO.setOrderNumber(slide.getOrderNumber());
    slideDTO.setText(slide.getText());
    slideDTO.setImageUrl(slide.getImageUrl());
    

    return slideDTO;
  }
  
  
  public List<SlidePublicDTO> sliteList2Dto(List<Slide> slideList) {
    List<SlidePublicDTO> slideDTOS=new ArrayList<>();
    for (Slide slide: slideList) {
      SlidePublicDTO slideDTO=slidetoSlidePublicDTO(slide);
      slideDTOS.add(slideDTO);
    }
    return slideDTOS;
  }
  
	public Slide slideDTOtoEntity(SlideDTO slideDTO) {
		System.out.println(slideDTO.toString());
		Slide slide=new Slide();
		slide.setImageUrl(slideDTO.getImageUrl());
		slide.setOrderNumber(slideDTO.getOrderNumber());
		slide.setOrganization(slideDTO.getOrganization());
		slide.setText(slideDTO.getText());
	
		return slide;
	}
	
}
