package com.alkemy.ong.controller;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.messages.DocumentationMessages;
import com.alkemy.ong.security.UserDetailServiceImpl;
import com.alkemy.ong.security.exception.UserAlreadyExistsException;
import com.alkemy.ong.security.payload.SignupRequest;
import com.alkemy.ong.service.MailService;
import com.alkemy.ong.service.Registration;
import com.alkemy.ong.service.UserDAO;
import com.alkemy.ong.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("auth")
public class AuthController extends BaseController {

    @Autowired
    private UserDAO userDAO;

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

    @Autowired
    private UserService userService;

    @PostMapping(path = "register", produces = "application/json")
    @Operation(summary = "User Register")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                description = DocumentationMessages.USER_CONTROLLER_RESPONSE_200_DESCRIPTION)
        ,
      @ApiResponse(responseCode = "400",
                description = DocumentationMessages.USER_CONTROLLER_RESPONSE_400_DESCRIPTION)
        ,
      @ApiResponse(responseCode = "403",
                description = DocumentationMessages.USER_CONTROLLER_RESPONSE_403_DESCRIPTION)
    })
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

        String content = registration.buildEmail(user.getFirstName() + " " + user.getLastName(),
                "https://alkemy.org");
        mailService.sendTextEmail(user.getEmail(), "You registered successfully", content);
        return ResponseEntity.created(uri).body(userDAO.create(user));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public Map<String, String> handleUserExistExceptions() {
        Map<String, String> errors = new HashMap<>();
        errors.put("mail", "The indicated email address is already in use");
        return errors;
    }

    // Este método debe estar según la OT133-25
    @PostMapping("/login")
    @Operation(summary = DocumentationMessages.AUTHENTICATION_CONTROLLER_LOGIN)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                description = DocumentationMessages.AUTHENTICATION_CONTROLLER_RESPONSE_200_DESCRIPTION)
        ,
      @ApiResponse(responseCode = "400",
                description = DocumentationMessages.AUTHENTICATION_CONTROLLER_RESPONSE_400_DESCRIPTION)
        ,
      @ApiResponse(responseCode = "401",
                description = DocumentationMessages.AUTHENTICATION_CONTROLLER_RESPONSE_401_DESCRIPTION)
        ,
      @ApiResponse(responseCode = "403",
                description = DocumentationMessages.AUTHENTICATION_CONTROLLER_RESPONSE_404_DESCRIPTION)
        ,
      @ApiResponse(responseCode = "500",
                description = DocumentationMessages.AUTHENTICATION_CONTROLLER_RESPONSE_500_DESCRIPTION)
    })
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
            }

        } catch (BadCredentialsException e) {
            response.put("Forbidden", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @GetMapping("/me")
    @Operation(summary = "User Profile")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                description = DocumentationMessages.USER_CONTROLLER_RESPONSE_200_DESCRIPTION)
        ,
      @ApiResponse(responseCode = "403",
                description = DocumentationMessages.USER_CONTROLLER_RESPONSE_403_DESCRIPTION)
    })
    public ResponseEntity<?> getAuthenticatedUserDetails(
            @RequestHeader(value = "Authorization") String authorizationHeader) {
        return new ResponseEntity<>(userService.getUserDetails(authorizationHeader), HttpStatus.OK);

    }

}
