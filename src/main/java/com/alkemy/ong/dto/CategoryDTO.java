package com.alkemy.ong.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "CategoryDTO")
public class CategoryDTO {

    @Schema(description = "Category name",required = true,example = "Family.")
    @NotBlank(message = "no es un valor valido.")
    @Size(message = "debe tene minimo 4 caracteres", min = 4)
    private String name;

    @Schema(description = "Category description",required = true,example = "This category is about family.")
    @NotBlank(message = "no es un valor valido.")
    @Size(message = "debe tene minimo 5 caracteres", min = 5)
    private String description;

    @Schema(description = "Category image.",required = false)
    private String image;

    @Schema(description = "Category Soft delete.",required = false,example = "Do not modify this field." )
    private boolean softDelete;

    @Schema(description = "Member image.",required = false,example = "Do not modify this field.")
    private String timestamps;
}
