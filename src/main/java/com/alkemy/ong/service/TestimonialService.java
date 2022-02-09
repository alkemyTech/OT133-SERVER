package com.alkemy.ong.service;

import java.util.List;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.dto.TestimonialIDDTO;

public interface TestimonialService {

  List<TestimonialDTO> read(Integer page);

  TestimonialIDDTO create(TestimonialDTO dto);

  TestimonialDTO findById(String id);

  TestimonialDTO update(String id, TestimonialDTO dto);

  void delete(String id);

}
