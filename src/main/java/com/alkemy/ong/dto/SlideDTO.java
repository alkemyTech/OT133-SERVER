package com.alkemy.ong.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.alkemy.ong.entity.Organization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SlideDTO {

	@NotBlank(message = "no es un valor valido.")
	private String text;
	
	@Min(message = "debe ser igual o mayor a 1", value = 1)
	private Integer orderNumber;
	
	private LocalDateTime timestamps;
	
	private String imageUrl;
	
	private Organization organization;
	
}
