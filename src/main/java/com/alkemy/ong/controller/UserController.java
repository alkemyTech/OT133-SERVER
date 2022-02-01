package com.alkemy.ong.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.alkemy.ong.entity.User;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.service.UserService;

@RestController
@RequestMapping("/auth/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
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
	
	
	@PatchMapping("/{id}")
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
