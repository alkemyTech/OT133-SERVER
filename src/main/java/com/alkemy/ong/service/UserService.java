package com.alkemy.ong.service;

import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.User;

public interface UserService {
	
	Iterable<User> listAll();
	
	UserDTO save(UserDTO dto);

	void deleteUser(String id); 

	boolean userExists(String id);
}
