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
@RequestMapping("/users")
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
	
}
