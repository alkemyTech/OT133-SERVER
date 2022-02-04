package com.alkemy.ong.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

  @Override
  public Iterable<User> listAll() {
    return userRepository.findAll();
  }

  @Override
  public UserDTO save(UserDTO dto) {
    User user = userMapper.toUser(dto);
    User userSave = userRepository.save(user);
    UserDTO result = userMapper.toUserDTO(userSave);
    return result;
  }

  public void deleteUser(String id) {
    Optional<User> u = userRepository.findById(id);
    if (u.isPresent()) {
      User user = u.get();
      userRepository.delete(user);
    }
  }

  public boolean userExists(String id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent())
      return true;
    return false;
  }

  @Transactional(readOnly = true)
  public Optional<User> getByEmail(String email) {
    return this.userRepository.findByEmail(email);
  }

  @Transactional
  public Optional<User> update(Map<Object, Object> fields, UUID id) {
    Optional<User> userOptional = this.userRepository.findById(id.toString());

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
}
