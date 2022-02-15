package com.alkemy.ong.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "MemberDTO")
public class MemberDTO {

	@Schema(description = "Member name",required = true,example = "Your name.")
	@NotBlank(message = "El nombre es obligatorio")
	private String name;

	@Schema(description = "Facebook profile url.",required = false, example = "myprofilefacebook.com")
	private String facebookUrl;

	@Schema(description = "Instagram profile url.",required = false, example = "myprofielinstagram.com")
	private String instagramUrl;

	@Schema(description = "Linkedin profile url.",required = false, example = "myprofilelinkedin.com")
	private String linkedinUrl;

	@Schema(description = "Member image.",required = true)
	@NotBlank(message = "La imagen es obligatoria")
	private String image;

	@Schema(description = "Member description.",required = false,example = "Who you are?What are you doing for the Organization?")
	private String description;

}
