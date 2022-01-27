package com.alkemy.ong.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryDTO {
    private String id;
    private String name;
    private String description;
    private String image;
    private LocalDateTime timestamps;
}
