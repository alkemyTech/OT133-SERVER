package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
