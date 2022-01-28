package com.alkemy.ong.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.entity.User;
import com.alkemy.ong.service.UserSerivce;

@RestController
@RequestMapping("/auth/users")
public class UserController {
	
	@Autowired
	private UserSerivce userSerivce;
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> listAll(){
		List<User> users = StreamSupport
				.stream(userSerivce.listAll().spliterator(), false)
				.collect(Collectors.toList());
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
}
