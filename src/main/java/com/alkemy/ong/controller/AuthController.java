package com.alkemy.ong.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.security.exception.UserAlreadyExistsException;
import com.alkemy.ong.security.payload.SignupRequest;
import com.alkemy.ong.service.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("auth")
public class AuthController extends BaseController {

	@Autowired
	private UserDAO userService;

	@PostMapping(path = "register", produces = "application/json")
	public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest)
			throws UserAlreadyExistsException {

		// Creacion de nuevo usuario
		User user = new User();
		user.setFirstName(signupRequest.getFirstname());
		user.setLastName(signupRequest.getLastname());
		user.setEmail(signupRequest.getEmail());
		user.setPassword(signupRequest.getPassword());

		// Creacion de la URI
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/auth/register").toUriString());

		return ResponseEntity.created(uri).body(userService.create(user));
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(UserAlreadyExistsException.class)
	public Map<String, String> handleUserExistExceptions() {
		Map<String, String> errors = new HashMap<>();
		errors.put("mail", "The indicated email address is already in use");
		return errors;
	}

}
