package com.alkemy.ong.service;

import com.alkemy.ong.dto.TestimonialDTO;

public interface TestimonialService {

  TestimonialDTO create(TestimonialDTO dto);

  TestimonialDTO findById(String id);

  TestimonialDTO update(String id, TestimonialDTO dto);

  void delete(String id);

}
