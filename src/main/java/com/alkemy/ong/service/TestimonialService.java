package com.alkemy.ong.service;

import javax.persistence.EntityNotFoundException;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.entity.Testimonial;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.repository.TestimonialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface TestimonialService {

  TestimonialDTO create(TestimonialDTO dto);

  TestimonialDTO findById(String id);

  TestimonialDTO update(String id, TestimonialDTO dto);

  void delete(String id);

}
