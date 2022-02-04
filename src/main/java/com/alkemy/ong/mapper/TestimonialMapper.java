package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.entity.Testimonial;
import org.springframework.stereotype.Component;

@Component
public class TestimonialMapper {

  public Testimonial toEntity(TestimonialDTO dto) {
    return new Testimonial(dto.getName(), dto.getImage(), dto.getContent());
  }

  public TestimonialDTO toDTO(Testimonial testimonial) {
    return new TestimonialDTO(testimonial.getName(), testimonial.getImage(),
        testimonial.getContent(), testimonial.getTimestamps());
  }

}
