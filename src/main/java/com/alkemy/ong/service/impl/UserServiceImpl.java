package com.alkemy.ong.service.impl;

import com.alkemy.ong.entity.User;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.MailService;
import com.alkemy.ong.service.Registration;
import com.alkemy.ong.service.UserService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    @Autowired
    Registration registration;

    //Luego reemplazar por UserDTO
    public User saveUser(User user) throws IOException {

        userRepository.save(user);

        String content = registration.buildEmail(user.getFirstName() + " " + user.getLastName(), "https://alkemy.org");
        mailService.sendTextEmail(user.getEmail(), "Registro correto - Grupo 133", content);

        return user;

    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public Optional<User> findByUserId(String id) throws FileNotFoundException{
        return userRepository.findById(id);
    }
}
