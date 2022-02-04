package com.alkemy.ong.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class MemberDTO {
  @NotEmpty(message = "El nombre es obligatorio")
  private String name;

  private String facebookUrl;

  private String instagramUrl;

  private String linkedinUrl;

  @NotEmpty(message = "La imagen es obligatoria")
  private String image;

  private String description;
  
  private LocalDateTime timestamps;

}
