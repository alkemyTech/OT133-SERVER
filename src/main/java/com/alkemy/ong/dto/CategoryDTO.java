package com.alkemy.ong.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryDTO {

	@NotBlank(message = "no es un valor valido.")
	@Size(message = "debe tene minimo 4 caracteres", min = 4)
    private String name;

	@NotBlank(message = "no es un valor valido.")
	@Size(message = "debe tene minimo 5 caracteres", min = 5)
    private String description;

    private String image;

    private boolean softDelete;

    private String timestamps;
}
