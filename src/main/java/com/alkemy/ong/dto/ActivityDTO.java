package com.alkemy.ong.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ActivityDTO {

  @NotBlank
  private String name;

  @NotBlank
  private String content;

  @NotBlank
  private String image;

  private String timestamps;

  private boolean softDelete = Boolean.FALSE;

}
