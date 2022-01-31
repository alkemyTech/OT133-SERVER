package com.alkemy.ong.controller;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.security.exception.UserAlreadyExistsException;
import com.alkemy.ong.security.payload.SignupRequest;

import com.alkemy.ong.service.MailService;
import com.alkemy.ong.service.Registration;
import com.alkemy.ong.service.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;


import com.alkemy.ong.security.UserDetailServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("auth")
public class AuthController extends BaseController {


  @Autowired
  private UserDAO userService;
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private MailService mailService;
  @Autowired
  private Registration registration;
  
  @Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailServiceImpl userDetails;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	//Este método debe estar según la OT133-25
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
				}
				
			}catch(BadCredentialsException e) {
				response.put("Forbidden", HttpStatus.FORBIDDEN);
			}
			return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
		}

  @PostMapping(path = "register", produces = "application/json")
  public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest)
    throws UserAlreadyExistsException, IOException {
    // Creacion de nuevo usuario
    User user = new User();
    user.setFirstName(signupRequest.getFirstname());
    user.setLastName(signupRequest.getLastname());
    user.setEmail(signupRequest.getEmail());
    user.setPassword(signupRequest.getPassword());


    // Creacion de la URI
    URI uri = URI.create(
        ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/register").toUriString());
    
    String content = registration.buildEmail(user.getFirstName() + " " + user.getLastName(), "https://alkemy.org");
    mailService.sendTextEmail(user.getEmail(),"You registered successfully", content);
    return ResponseEntity.created(uri).body(userService.create(user));
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

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(UserAlreadyExistsException.class)
  public Map<String, String> handleUserExistExceptions() {
    Map<String, String> errors = new HashMap<>();
    errors.put("mail", "The indicated email address is already in use");
    return errors;
  }
}
