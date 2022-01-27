package com.alkemy.ong.service;

import com.alkemy.ong.entity.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public interface UserService {

    //Luego reemplazar por UserDTO
    User saveUser(User user) throws IOException;

    void deleteUser(User user);  

    Optional<User> findByUserId(String id) throws FileNotFoundException; 
}
