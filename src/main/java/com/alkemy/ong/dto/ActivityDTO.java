package com.alkemy.ong.dto;


import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityDTO {

  @NotNull
  private String name;

  @NotNull
  private String content;

  @NotNull
  private String image;

  private String timestamps;

  private boolean softDelete = Boolean.FALSE;

}
