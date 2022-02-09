package com.alkemy.ong.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ContactDTO {
    
    @NotBlank(message = "There must be a name, it can't be empty")
    private String name;

    @NotBlank(message = "There must be an email, it can't be empty")
    private String email;

    @NotNull(message = "There must be a phone, it can't be empty")
    private Long phone;

    @NotBlank(message = "There must be a message, it can't be empty")
    private String message;

    private LocalDateTime timestamps;

}
