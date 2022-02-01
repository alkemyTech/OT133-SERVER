package com.alkemy.ong.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.User;

@Component
public class UserMapper {

	public UserDTO toUserDTO(User user) {
		return new UserDTO(
							user.getId(), 
							user.getFirstName(), 
							user.getLastName(), 
							user.getEmail(), 
							user.getPhoto(),
							user.getPassword(), 
							user.getRoleId());
	}

	public User toUser(UserDTO userDTO) {
		User user = new User();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());

		return user;
	}

	public User userDTO2Entity(UserDTO dto) {
		User user = new User();
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setPhoto(dto.getPhoto());
		user.setRoleId(dto.getRoles());
		user.setTimestamps(LocalDateTime.now());
		return user;
	}

	public UserDTO entity2userDTO(User user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setEmail(user.getEmail());
		dto.setPassword(user.getPassword());
		dto.setPhoto(user.getPhoto());
		dto.setRoles(user.getRoleId());
		return dto;
	}
}
