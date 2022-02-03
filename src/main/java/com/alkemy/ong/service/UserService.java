package com.alkemy.ong.service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.User;

public interface UserService {
	
	Iterable<User> listAll();
	
	UserDTO save(UserDTO dto);

	void deleteUser(String id); 

	boolean userExists(String id);
	
	Optional<User> update(Map<Object, Object> fields, UUID id);
	
	Optional<User> getByEmail(String email);
}
