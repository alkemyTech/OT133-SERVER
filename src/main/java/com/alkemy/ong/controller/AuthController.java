package com.alkemy.ong.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alkemy.ong.entity.Rol;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.enums.ERole;
import com.alkemy.ong.repository.RolRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.security.payload.MessageResponse;
import com.alkemy.ong.security.payload.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.security.UserDetailServiceImpl;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailServiceImpl userDetails;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody String email,@RequestBody String password){
		Map<String,Object> response = new HashMap<>();
		boolean authenticated= false;
		
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			UserDetails userDetails = this.userDetails.loadUserByUsername(email);
			
			if(userDetails != null) {
				authenticated = this.passwordEncoder.matches(password, userDetails.getPassword());
				if(authenticated) {
					response.put("ok", userDetails);
					return new ResponseEntity<>(response,HttpStatus.OK);
				}
			}else {
				response.put("ok", Boolean.FALSE);
			}
			
		}catch(BadCredentialsException e) {
			response.put("ok", Boolean.FALSE);
		}
		return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUserName(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
				signUpRequest.getLastname(),
				signUpRequest.getEmail(),
				passwordEncoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Rol> roles = new HashSet<>();

		if (strRoles == null) {
			Rol userRole = rolRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":
						Rol adminRole = rolRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
					case "mod":
						Rol modRole = rolRepository.findByName(ERole.ROLE_MODERATOR)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);

						break;
					default:
						Rol userRole = rolRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
