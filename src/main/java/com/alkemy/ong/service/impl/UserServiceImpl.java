package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRepository userRepository;

    public UserDTO save(UserDTO dto) {
         User user = userMapper.toUser(dto);
         User userSave = userRepository.save(user);
         UserDTO result = userMapper.toUserDTO(userSave);
         return result;
    }
}
