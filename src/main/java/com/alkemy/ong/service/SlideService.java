package com.alkemy.ong.service;

import java.util.Optional;

import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.entity.Slide;

public interface SlideService {
    
	Optional<Slide> getbyId(String id);
	
	Optional<SlideDTO> update(SlideDTO slideDTO,String id);
	
	void deleteSlide(String id) throws Exception;
	
	public SlideDTO saveSlide(SlideDTO slide) throws Exception;

	Iterable<Slide> findAllDefined();
  
  SlideDTO findById(String id); 


}
