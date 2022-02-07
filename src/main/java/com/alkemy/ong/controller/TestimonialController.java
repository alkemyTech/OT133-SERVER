package com.alkemy.ong.controller;

import java.net.URI;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController extends BaseController {

  // --------------------------------------------------------------------------------------------
  // Autowireds
  // --------------------------------------------------------------------------------------------
  @Autowired
  private TestimonialService testimonialService;

  // --------------------------------------------------------------------------------------------
  // Get
  // --------------------------------------------------------------------------------------------

  // --------------------------------------------------------------------------------------------
  // Post
  // --------------------------------------------------------------------------------------------

  @PostMapping(produces = "application/json")
  public ResponseEntity<TestimonialDTO> create(@Validated @RequestBody TestimonialDTO testimonial) {

    URI uri = URI.create(
        ServletUriComponentsBuilder.fromCurrentContextPath().path("/members").toUriString());

    return ResponseEntity.created(uri).body(testimonialService.create(testimonial));
  }

  // --------------------------------------------------------------------------------------------
  // Update
  // --------------------------------------------------------------------------------------------

  @PutMapping(path = "/{id}", produces = "application/json")
  public ResponseEntity<TestimonialDTO> update(@PathVariable String id,
      @Validated @RequestBody TestimonialDTO testimonial) {
    return ResponseEntity.ok(testimonialService.update(id, testimonial));
  }


}
