package com.alkemy.ong.controller;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.security.UserDetailServiceImpl;


@RestController
@RequestMapping(path = "/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;  
	
	@Autowired
	private UserDetailServiceImpl userDetails;   
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder; 
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestParam String email,@RequestParam String password){
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
				response.put("Forbidden", HttpStatus.FORBIDDEN);
				return ResponseEntity.badRequest().body(response);
			}
		}catch(BadCredentialsException e) {
			response.put("Forbidden", HttpStatus.FORBIDDEN);
			return ResponseEntity.badRequest().body(response);
		}
		response.put("ok", Boolean.FALSE);
		return ResponseEntity.badRequest().body(response);
	}
}
