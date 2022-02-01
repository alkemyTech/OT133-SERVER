package com.alkemy.ong.dto;


public class UserDTO {


	
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

	private String firstName;
	private String lastName;
	private String email;
	
}
