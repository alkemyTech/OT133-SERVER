package com.alkemy.ong.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryDTO {

	@NotEmpty(message = "no es un valor admitido")
    private String name;

	@NotEmpty(message = "no es un valor admitido")
    private String description;

    private String image;

    private boolean softDelete;

    private String timestamps;
}
