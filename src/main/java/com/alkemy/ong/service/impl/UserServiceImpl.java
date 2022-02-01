package com.alkemy.ong.service.impl;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.alkemy.ong.security.token.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public Iterable<User> listAll() {
		return userRepository.findAll();
	}

	@Override
	public UserDTO save(UserDTO dto) {
		User user = userMapper.userDTO2Entity(dto);
		User userSave = userRepository.save(user);
		UserDTO result = userMapper.entity2userDTO(userSave);
		return result;
	}

	@Transactional(readOnly = true)
	public Optional<User> getById(UUID id) {
		return this.userRepository.findById(id.toString());
	}

	@Transactional(readOnly = true)
	public Optional<User> getByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}

	@Override
	public Optional<User> update(Map<Object, Object> fields, UUID id) {
		Optional<User> userOptional = this.getById(id);

		if (!userOptional.isPresent()) {
			return Optional.empty();
		} else {
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findField(User.class, (String) key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, userOptional.get(), value);
			});
			return Optional.of(this.userRepository.save(userOptional.get()));
		}

	}
	
	@Override
	  public UserDTO getUserDetails(String authorizationHeader) {
	    String username = jwtUtil.extractUsername(authorizationHeader);
	    User user = userRepository.findByEmail(username).get();
	    UserDTO result = userMapper.entity2userDTO(user);
	    return result;
	  }


}
