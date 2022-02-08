package com.alkemy.ong.service;

import java.util.List;
import java.util.Optional;

import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.entity.Slide;

public interface SlideService {
    
	Optional<Slide> getbyId(String id);
	
	Optional<SlideDTO> update(SlideDTO slideDTO,String id);
	
	void deleteSlide(String id) throws Exception;
	
	List<Slide> findAllDefined();
  
  SlideDTO findById(String id); 

}
