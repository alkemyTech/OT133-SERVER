package com.alkemy.ong.service;

import com.alkemy.ong.entity.User;

import java.io.IOException;

public interface UserService {

    //Luego reemplazar por UserDTO
    User saveUser(User user) throws IOException;

    void deleteUser(String email);  

    User findByUserId(String id); 

    User findByEmail(String email);

    String traerEmail(String id);
}
