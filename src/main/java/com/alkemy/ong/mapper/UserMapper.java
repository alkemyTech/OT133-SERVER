package com.alkemy.ong.mapper;

import java.time.LocalDateTime;

import com.alkemy.ong.dto.UserDTOAll;
import org.springframework.stereotype.Component;

import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.User;

@Component
public class UserMapper {

  public UserDTO toUserDTO(User user) {
    return new UserDTO(user.getFirstName(), user.getLastName(), user.getEmail());
  }

  public User toUser(UserDTO userDTO) {
    User user = new User();
    user.setFirstName(userDTO.getFirstName());
    user.setLastName(userDTO.getLastName());
    user.setEmail(userDTO.getEmail());

    return user;
  }

  public User userDTO2Entity(UserDTOAll dto) {
    User user = new User();
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    user.setEmail(dto.getEmail());
    user.setPassword(dto.getPassword());
    user.setPhoto(dto.getPhoto());
    user.setRoles(dto.getRoles());
    user.setTimestamps(LocalDateTime.now());
    return user;
  }

  public UserDTOAll entity2userDTO(User user) {
    UserDTOAll dto = new UserDTOAll();
    dto.setId(user.getId());
    dto.setFirstName(user.getFirstName());
    dto.setLastName(user.getLastName());
    dto.setEmail(user.getEmail());
    dto.setPassword(user.getPassword());
    dto.setPhoto(user.getPhoto());
    dto.setRoles(user.getRoles());
    return dto;
  }


}
