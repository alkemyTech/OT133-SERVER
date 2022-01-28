package com.alkemy.ong.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewDTO {
    private String id;
    private String name;
    private String content;
    private String image;
    private String categoryId;
    private LocalDateTime timestamps;
}
