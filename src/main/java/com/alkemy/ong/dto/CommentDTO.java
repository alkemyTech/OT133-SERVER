package com.alkemy.ong.dto;


import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDTO {

	@NotBlank(message = "No es un valor aceptable")
	private String userId;
	
	@NotBlank(message = "No es un valor aceptable")
	private String body;
	
	@NotBlank(message = "No es un valor aceptable")
	private String newId;

}
