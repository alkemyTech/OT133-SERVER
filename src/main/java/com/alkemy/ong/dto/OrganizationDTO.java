package com.alkemy.ong.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDTO {

  @NotEmpty(message = "Name cannot be empty")
  @NotBlank
  @NotNull
  @Size(min = 4, message = "Name should be at least 4 characters")
  @Pattern(regexp = "[a-zA-Z]+", message = "Name must contain only letters")
  private String name;

  @NotNull
  @NotEmpty(message = "An image must be loaded")
  @NotBlank
  private String image;

  private String address;

  private Integer phone;

  @NotEmpty
  @NotNull
  @NotBlank
  @Email
  private String email;

  @NotEmpty
  @NotBlank
  private String welcomeText;

  private String aboutUsText;

  private String facebookUrl;

  private String linkedinUrl;

  private String instagramUrl;

  private LocalDateTime timestamps;

}
