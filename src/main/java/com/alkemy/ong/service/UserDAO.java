package com.alkemy.ong.service;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.alkemy.ong.entity.User;
import com.alkemy.ong.repository.UserRepository;

@Service
public class UserDAO {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional(readOnly=true)
	public Optional<User> getByEmail(String email){
		return this.userRepository.findByEmail(email);
	}
	
	@Transactional(readOnly=true)
	public Optional<User> getById(UUID id){
		return this.userRepository.findById(id.toString());
	}
	
	public User save(User user) {
		return this.userRepository.save(user);
	}
	
	
	public Optional<User> update(Map<Object,Object> fields, UUID id) {
		Optional<User> userOptional = this.getById(id);
		
		if(!userOptional.isPresent()) {
			return Optional.empty();
		}else {
			fields.forEach((key, value) ->{
				Field field = ReflectionUtils.findField(User.class, (String) key);
				field.setAccessible(true);
				ReflectionUtils.setField(field,userOptional.get(), value);
			});
			return Optional.of(this.save(userOptional.get()));
		}

	}
}
