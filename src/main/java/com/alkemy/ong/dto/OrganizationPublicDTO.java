package com.alkemy.ong.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrganizationPublicDTO {
  private String name;

  private String image;

  private String address;

  private Integer phone;

  private List<SlidePublicDTO> slides=new ArrayList<>();

  public void addSlides(SlidePublicDTO SlideDTOAdd) {
    this.slides.add(SlideDTOAdd);
  }

}
