package com.alkemy.ong.controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.alkemy.ong.entity.User;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.security.UserDetailServiceImpl;
import com.alkemy.ong.service.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailServiceImpl userDetails;
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDAO userDAO;

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
		Map<String, Object> response = new HashMap<>();
		boolean authenticated = false;

		try {
			this.authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			UserDetails userDetails = this.userDetails.loadUserByUsername(email);

			if (userDetails != null) {
				authenticated = this.passwordEncoder.matches(password, userDetails.getPassword());
				if (authenticated) {
					response.put("ok", userDetails);
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
			} else {
				response.put("Forbidden", HttpStatus.FORBIDDEN);
				return ResponseEntity.badRequest().body(response);
			}
		} catch (BadCredentialsException e) {
			response.put("Forbidden", HttpStatus.FORBIDDEN);
			return ResponseEntity.badRequest().body(response);
		}
		response.put("ok", Boolean.FALSE);
		return ResponseEntity.badRequest().body(response);
	}
	
	
	@PatchMapping("/users/{id}")
	public ResponseEntity<?> updateUser(@RequestBody Map<Object,Object> fields, @PathVariable UUID id){
		Map<String, Object> response = new HashMap<>();
		Optional<User> userOptional = this.userDAO.update(fields, id);
		
		if(!userOptional.isPresent()) {
			response.put("Error", String.format("User with ID %s not found.", id));
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}else {
			response.put("ok",this.userMapper.toUserDTO(userOptional.get()));
			return ResponseEntity.ok(response);
		}
		
	}

	// @PostMapping("/register")
	// public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){

	// //verificacion del mail
	// if(userRepository.existsByEmail(signupRequest.getEmail())){
	// return ResponseEntity
	// .badRequest()
	// .body("Error: Email is already in use!");
	// }

	// //Creacion de nuevo usuario
	// User user = new User();
	// user.setFirstName(signupRequest.getFirstname());
	// user.setLastName(signupRequest.getLastname());
	// user.setEmail(signupRequest.getEmail());
	// user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

	// Rol userRole = rolRepository.findByName(Roles.ROL_USER);
	// user.setRoleId(userRole);
	// userRepository.save(user);

	// return new ResponseEntity<String>("User registered successfully", HttpStatus.OK);
	// }

}
