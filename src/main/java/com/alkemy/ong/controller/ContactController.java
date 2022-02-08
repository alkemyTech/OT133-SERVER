package com.alkemy.ong.controller;

import java.io.IOException;
import java.util.*;

import com.alkemy.ong.dto.ContactDTO;
import com.alkemy.ong.exception.ContactException;
import com.alkemy.ong.sendgrid.config.EmailRequest;
import com.alkemy.ong.sendgrid.config.EmailService;
import com.alkemy.ong.service.ContactService;
import com.sendgrid.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
public class ContactController {
	
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    EmailService emailService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> createContact(@Validated @RequestBody ContactDTO contactDTO) throws ContactException, IOException{
        try{
            ContactDTO contact = contactService.save(contactDTO);
            
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setTo(contactDTO.getEmail());
            emailRequest.setSubject("Your contact form is registered successfully");
            emailRequest.setBody("Hi "+ contactDTO.getName() + 
            		", we are very happy that you are part of Somos Mas\nThanks for you contact.");
            
            EmailRequest emailConsulta = new EmailRequest();
            emailConsulta.setTo("alkemysomosmas@gmail.com");
            emailConsulta.setSubject("Consult Contact: " + contactDTO.getEmail());
            emailConsulta.setBody(contactDTO.getMessage());
            

            Response response = emailService.sendMail(emailRequest);
            emailService.sendMail(emailConsulta);
            
        	if(response.getStatusCode() == 200 || response.getStatusCode() == 202) {
        		return ResponseEntity.status(HttpStatus.OK)
        				.body(contact);
        	}else {
        		return new ResponseEntity<>("Failed to sent", HttpStatus.NOT_FOUND);
        	}
            
        } catch ( Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Exception: " + e.getLocalizedMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ContactException.class)
	public Map<String, String> handlerContactExceptions() {
    Map<String, String> error = new HashMap<>();
    error.put( "Ok: " + Boolean.FALSE , "Cannot process the request, check the petition(timestamps must be null)");
    return error;
  	}
    
    
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
	@GetMapping
	public ResponseEntity<?> getAll(){
		Map<String, Object> response = new HashMap<>();
		
		Optional<List<ContactDTO>> contactsDTO = this.contactService.getAll();
		
		if(contactsDTO.isEmpty()) {
			response.put("Error","Contact list is empty.");
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}
		
		response.put("ok", contactsDTO.get());
		return ResponseEntity.ok(response);
	}
	
}
