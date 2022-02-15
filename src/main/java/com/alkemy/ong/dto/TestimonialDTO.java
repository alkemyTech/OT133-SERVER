package com.alkemy.ong.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "TestimonialDTO")
public class TestimonialDTO {

  @NotBlank
  @NotNull
  @NotEmpty
  @Schema(description = "Testimonial's name", required = true, example = "Testimonial Example")
  private String name;

  @Schema(description = "Image", required = false, example = "image.jpeg")
  private String image;

  @NotBlank
  @NotNull
  @NotEmpty
  @Schema(description = "Testimonial's content", required = true)
  private String content;

  @Schema(description = "Creation date", required = false)
  LocalDateTime timestamps;

}
