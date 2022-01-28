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

import com.alkemy.ong.dto.UserDTO;

@RestController
public class UserController {

    @Autowired 
    private UserService service;

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        Map<String, Object> response = new HashMap<>();
        try{
            UserDTO u = new UserDTO (null, null, service.traerEmail(id));
            if(u.email != null) {
                service.deleteUser(u.email);
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
