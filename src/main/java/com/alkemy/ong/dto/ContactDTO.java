package com.alkemy.ong.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContactDTO {

	private String name;
	private Long phone;
	private String email;
	private String message;
	private LocalDateTime timestamps;
}
