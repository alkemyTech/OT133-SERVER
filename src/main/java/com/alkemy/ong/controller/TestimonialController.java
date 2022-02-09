package com.alkemy.ong.controller;

import java.net.URI;
import java.util.List;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.dto.TestimonialIDDTO;
import com.alkemy.ong.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping(produces = "application/json")
  public ResponseEntity<List<TestimonialDTO>> read(@RequestParam(required = false) Integer page) {

    return ResponseEntity.ok(testimonialService.read(page));
  }

  // --------------------------------------------------------------------------------------------
  // Post
  // --------------------------------------------------------------------------------------------

  @PreAuthorize("hasAuthority('ROL_ADMIN')")
  @PostMapping(produces = "application/json")
  public ResponseEntity<TestimonialDTO> create(@Validated @RequestBody TestimonialDTO testimonial) {

    TestimonialIDDTO dtoObj = testimonialService.create(testimonial);

    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
        .path(String.format("/testimonials/%s", dtoObj.getId())).toUriString());

    return ResponseEntity.created(uri).body(dtoObj.getDto());
  }

  // --------------------------------------------------------------------------------------------
  // Update
  // --------------------------------------------------------------------------------------------

  @PreAuthorize("hasAuthority('ROL_ADMIN')")
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
