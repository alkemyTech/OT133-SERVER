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
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserMapper userMapper;
	
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

	@DeleteMapping("/{id}")	
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
	
	
	 @PatchMapping("/users/{id}")
	  public ResponseEntity<?> updateUser(@RequestBody Map<Object, Object> fields,
	      @PathVariable UUID id) {
	    Map<String, Object> response = new HashMap<>();
	    Optional<User> userOptional = this.userService.update(fields, id);

	    if (!userOptional.isPresent()) {
	      response.put("Error", String.format("User with ID %s not found.", id));
	      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    } else {
	      response.put("ok", this.userMapper.toUserDTO(userOptional.get()));
	      return ResponseEntity.ok(response);
	    }

	  }
}
