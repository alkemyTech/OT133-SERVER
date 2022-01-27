package com.alkemy.ong.controller;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.alkemy.ong.entity.User;
import com.alkemy.ong.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired 
    private UserService service;

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        Map<String, Object> response = new HashMap<>();
        
        try{
            Optional<User> user = service.findByUserId(id);
            if(user.isPresent()) {
                User userPresent = user.get();
                service.deleteUser(userPresent);
                response.put("User eliminado", "Id: " + id);
                return ResponseEntity.ok(response);
            }
        } catch (FileNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        response.put("Ok", Boolean.FALSE);
        return ResponseEntity.ok().body(response);
    }
}
