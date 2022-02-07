package com.alkemy.ong.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ContactDTO {
    
    @NotBlank(message = "There must be a name, it can't be empty")
    private String name;

    @NotBlank(message = "There must be an email, it can't be empty")
    private String email;

    private Long phone;
    private String message;
    private LocalDateTime timestamps;

}
