package com.alkemy.ong.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ActivityDTO {

    private String name;

    private String content;

    private String image;

    private String timestamps;

    private boolean softDelete = Boolean.FALSE;

}