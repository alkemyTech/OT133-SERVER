package com.alkemy.ong.service.impl;

import java.util.Optional;
import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.entity.Slide;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SlideServiceImpl implements SlideService {

  @Autowired
  private SlideMapper slideMapper;

  @Autowired
  private SlideRepository slideRepository;


  @Override
  public Optional<Slide> getbyId(String id) {
    Optional<Slide> optSlide = this.slideRepository.findById(id);
    if (!optSlide.isPresent()) {
      return Optional.empty();
    }
    return optSlide;
  }

  @Override
  public void deleteSlide(String id) throws Exception {
    Optional<Slide> slideOptional = slideRepository.findById(id);
    if (slideOptional.isPresent()) {
      Slide slide = slideOptional.get();
      slideRepository.delete(slide);
    } else
      throw new Exception("The specified slide does not exist");
  }

  @Override
  public Optional<SlideDTO> update(SlideDTO slideDTO, String id) {
    Optional<Slide> optSlide = this.slideRepository.findById(id);

    if (!optSlide.isPresent()) {
      return Optional.empty();
    }
    Slide slideSave = this.slideMapper.toSlide(slideDTO, optSlide.get());
    return Optional.of(this.slideMapper.toSlideDTO(this.slideRepository.save(slideSave)));

  }

  @Override
  @Transactional(readOnly = true)
  public Iterable<Slide> findAllDefined() {
    return slideRepository.findAllDefined();
  }

  public SlideDTO findById(String id) {
    Slide slide = slideRepository.findById(id).get();
    return slideMapper.toSlideDTO(slide);
  }
}
