package com.alkemy.ong.service;

import javax.persistence.EntityNotFoundException;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.entity.Testimonial;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.repository.TestimonialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestimonialService {

  // --------------------------------------------------------------------------------------------
  // Autowireds
  // --------------------------------------------------------------------------------------------

  @Autowired
  private TestimonialRepository testimonialRepository;

  @Autowired
  private TestimonialMapper testimonialMapper;

  // --------------------------------------------------------------------------------------------
  // Create
  // --------------------------------------------------------------------------------------------

  public TestimonialDTO create(TestimonialDTO dto) {
    Testimonial testimonial = testimonialRepository.save(testimonialMapper.toEntity(dto));
    return testimonialMapper.toDTO(testimonial);
  }

  // --------------------------------------------------------------------------------------------
  // Read
  // --------------------------------------------------------------------------------------------

  // --------------------------------------------------------------------------------------------
  // Update
  // --------------------------------------------------------------------------------------------

  public TestimonialDTO update(String id, TestimonialDTO dto) {

    Testimonial testimonial =
        testimonialRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    testimonial.setName(dto.getName());
    testimonial.setContent(dto.getImage());
    testimonial.setImage(dto.getImage());

    return testimonialMapper.toDTO(testimonialRepository.save(testimonial));
  }

  // --------------------------------------------------------------------------------------------
  // Delete
  // --------------------------------------------------------------------------------------------

}
