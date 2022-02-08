package com.alkemy.ong.controller;

import java.net.URI;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

  // --------------------------------------------------------------------------------------------
  // Delete
  // --------------------------------------------------------------------------------------------

  @PreAuthorize("ROL_ADMIN")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    if (testimonialService.findById(id) == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    testimonialService.delete(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
