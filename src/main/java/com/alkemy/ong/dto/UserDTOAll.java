package com.alkemy.ong.dto;

import java.util.List;

import com.alkemy.ong.entity.Rol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTOAll {

  private String id;
  private String firstName;
  private String lastName;
  private String email;
  private String photo;
  private String password;
  private List<Rol> roles;


}
