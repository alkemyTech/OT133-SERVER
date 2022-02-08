package com.alkemy.ong.service;

import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.dto.TestimonialIDDTO;

public interface TestimonialService {

  TestimonialIDDTO create(TestimonialDTO dto);

  TestimonialDTO findById(String id);

  TestimonialDTO update(String id, TestimonialDTO dto);

  void delete(String id);

}
