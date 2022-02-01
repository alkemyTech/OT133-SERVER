package com.alkemy.ong.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
    @Autowired
    UserMapper userMapper;
	
	@Autowired
	UserRepository userRepository;
	
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


}
