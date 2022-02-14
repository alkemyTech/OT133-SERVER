package com.alkemy.ong.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.dto.TestimonialIDDTO;
import com.alkemy.ong.service.TestimonialService;
import com.alkemy.ong.messages.DocumentationMessages;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
  @Operation(summary = DocumentationMessages.TESTIMONIAL_CONTROLLER_SUMMARY_LIST)
  @ApiResponses( value = { 
    @ApiResponse(responseCode = "200",
    description = DocumentationMessages.TESTIMONIAL_CONTROLLER_RESPONSE_200_DESCRIPTION)
  })
  public ResponseEntity<?> read(@RequestParam(required = false) Integer page) {

    List<TestimonialDTO> currentPage = testimonialService.read(page);
    Map<String, Object> responseData = new HashMap<>();

    if (Objects.isNull(page)) {
      return ResponseEntity.ok(currentPage);
    }

    boolean hasNext = !testimonialService.read(page + 1).isEmpty();
    String currentContextPath = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();

    if (hasNext) {
      String nextPath = String.format("/testimonials?page=%d", page + 1);
      responseData.put("next", currentContextPath.concat(nextPath));
    }

    if (page > 0) {
      String previousPath = String.format("/testimonials?page=%d", page - 1);
      responseData.put("previous", currentContextPath.concat(previousPath));
    }

    responseData.put("content", currentPage);

    return ResponseEntity.ok(responseData);
  }

  // --------------------------------------------------------------------------------------------
  // Post
  // --------------------------------------------------------------------------------------------

  @PreAuthorize("hasAuthority('ROL_ADMIN')")
  @PostMapping(produces = "application/json")
  @Operation(summary = DocumentationMessages.TESTIMONIAL_CONTROLLER_SUMMARY_CREATE)
  @ApiResponses( value = { 
    @ApiResponse(responseCode = "201",
    description = DocumentationMessages.TESTIMONIAL_CONTROLLER_RESPONSE_201_DESCRIPTION),
  })
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
  @Operation(summary = DocumentationMessages.TESTIMONIAL_CONTROLLER_SUMMARY_UPDATE)
  @ApiResponses( value = { 
    @ApiResponse(responseCode = "200",
    description = DocumentationMessages.TESTIMONIAL_CONTROLLER_RESPONSE_200_DESCRIPTION),
  })
  public ResponseEntity<TestimonialDTO> update(@PathVariable String id,
      @Validated @RequestBody TestimonialDTO testimonial) {
    return ResponseEntity.ok(testimonialService.update(id, testimonial));
  }

  // --------------------------------------------------------------------------------------------
  // Delete
  // --------------------------------------------------------------------------------------------

  @PreAuthorize("ROL_ADMIN")
  @DeleteMapping("/{id}")
  @Operation(summary = DocumentationMessages.TESTIMONIAL_CONTROLLER_SUMMARY_DELETE)
  @ApiResponses( value = { 
    @ApiResponse(responseCode = "200",
    description = DocumentationMessages.TESTIMONIAL_CONTROLLER_RESPONSE_200_DESCRIPTION),
    @ApiResponse(responseCode = "400",
    description = DocumentationMessages.TESTIMONIAL_CONTROLLER_RESPONSE_400_DESCRIPTION)
  })
  public ResponseEntity<Void> delete(@PathVariable String id) {
    if (testimonialService.findById(id) == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    testimonialService.delete(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
