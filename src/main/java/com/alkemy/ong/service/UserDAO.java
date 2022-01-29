package com.alkemy.ong.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ong.entity.User;
import com.alkemy.ong.repository.UserRepository;

@Service
public class UserDAO {

	@Autowired
	private UserRepository userRepository;
	
	public Optional<User> getByEmail(String email){
		return this.userRepository.findByEmail(email);
	}
}
