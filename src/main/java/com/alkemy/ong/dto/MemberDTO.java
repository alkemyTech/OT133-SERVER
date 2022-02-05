package com.alkemy.ong.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberDTO {
  
  @NotBlank(message = "El nombre es obligatorio")
  private String name;

  private String facebookUrl;

  private String instagramUrl;

  private String linkedinUrl;

  @NotBlank(message = "La imagen es obligatoria")
  private String image;

  private String description;

}
