package com.alkemy.ong.dto;


import javax.validation.constraints.NotBlank;
import lombok.Data;

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
