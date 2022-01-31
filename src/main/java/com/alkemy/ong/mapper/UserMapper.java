package com.alkemy.ong.mapper;

public class UserMapper {

import org.springframework.stereotype.Component;

import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.User;

@Component
public class UserMapper {

	public UserDTO toUserDTO(User user) {
		return new UserDTO(user.getFirstName(),user.getLastName(),user.getEmail());
	}
	
	public User toUser(UserDTO userDTO) {
		User user = new User();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		
		return user;
	}
}
