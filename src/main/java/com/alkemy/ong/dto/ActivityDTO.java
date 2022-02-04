package com.alkemy.ong.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ActivityDTO {

    @NotNull
    private String name;

    @NotNull
    private String content;

    @NotNull
    private String image;

    private String timestamps;

    private boolean softDelete = Boolean.FALSE;

}