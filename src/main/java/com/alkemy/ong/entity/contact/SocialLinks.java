package com.alkemy.ong.entity.contact;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SocialLinks {

  @Column(name = "facebook_url")
  private String facebookUrl;

  @Column(name = "linkedin_url")
  private String linkedinUrl;

  @Column(name = "instagram_url")
  private String instagramUrl;
}
