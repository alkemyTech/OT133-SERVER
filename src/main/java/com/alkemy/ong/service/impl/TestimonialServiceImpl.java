package com.alkemy.ong.service.impl;

import javax.persistence.EntityNotFoundException;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.dto.TestimonialIDDTO;
import com.alkemy.ong.entity.Testimonial;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestimonialServiceImpl implements TestimonialService {

  @Autowired
  private TestimonialMapper testimonialMapper;

  @Autowired
  private TestimonialRepository testimonialRepository;

  // --------------------------------------------------------------------------------------------
  // Create
  // --------------------------------------------------------------------------------------------

  public TestimonialIDDTO create(TestimonialDTO dto) {
    Testimonial testimonial = testimonialRepository.save(testimonialMapper.toEntity(dto));

    return new TestimonialIDDTO(testimonial.getId(), testimonialMapper.toDTO(testimonial));
  }

  // --------------------------------------------------------------------------------------------
  // Read
  // --------------------------------------------------------------------------------------------

  public TestimonialDTO findById(String id) {
    Testimonial testimonial = testimonialRepository.findById(id).get();
    return testimonialMapper.toDTO(testimonial);
  }

  // --------------------------------------------------------------------------------------------
  // Update
  // --------------------------------------------------------------------------------------------

  public TestimonialDTO update(String id, TestimonialDTO dto) {

    Testimonial testimonial =
        testimonialRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    testimonial.setName(dto.getName());
    testimonial.setContent(dto.getContent());
    testimonial.setImage(dto.getImage());

    return testimonialMapper.toDTO(testimonialRepository.save(testimonial));
  }

  // --------------------------------------------------------------------------------------------
  // Delete
  // --------------------------------------------------------------------------------------------

  public void delete(String id) {
    Testimonial testimonial = testimonialRepository.findById(id).get();
    testimonialRepository.delete(testimonial);
  }

}
