package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryDTO {

    private String name;

    private String description;

    private String image;

    private boolean softDelete;

    private String timestamps;
}
