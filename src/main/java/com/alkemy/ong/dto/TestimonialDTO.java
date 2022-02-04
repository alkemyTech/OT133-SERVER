package com.alkemy.ong.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestimonialDTO {

  @NotBlank
  @NotNull
  @NotEmpty
  private String name;

  private String image;

  @NotBlank
  @NotNull
  @NotEmpty
  private String content;

  LocalDateTime timestamps;
}
