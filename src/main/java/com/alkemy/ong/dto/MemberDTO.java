package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDTO {
  
  private String id;
  private String name;
  private String facebookUrl;
  private String instagramUrl;
  private String linkedinUrl;
  private String image;
  private String description;
  
}
