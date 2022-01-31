package com.alkemy.ong.controller;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.alkemy.ong.entity.User;
import com.alkemy.ong.exception.UserException;
import com.alkemy.ong.service.UserService;

@RestController
@RequestMapping("/auth/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> listAll(){
		try {
			List<User> users = StreamSupport
				.stream(userService.listAll().spliterator(), false)
				.collect(Collectors.toList());
	
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (BadRequest ex) {
			return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/user/{id}")	
    public ResponseEntity<?> deleteUser(@PathVariable String id) throws UserException, IOException{
        Map<String, Object> response = new HashMap<>();
        if(userService.userExists(id)) {
            userService.deleteUser(id);
            response.put("User eliminado", "Id: " + id);
            return ResponseEntity.ok(response);
        }
		response.put("Problem to ejecute process", HttpStatus.CONFLICT);
		return ResponseEntity.ok().body(response);
    }

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserException.class)
	public Map<String, String> handleUserNotFoundExceptions() {
    Map<String, String> error = new HashMap<>();
    error.put( "Ok: " + Boolean.FALSE , "Could not find user by the given ID");
    return error;
  }
	
}
