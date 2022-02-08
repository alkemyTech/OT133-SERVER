package com.alkemy.ong.controller;

import java.io.IOException;
import java.util.*;

import com.alkemy.ong.dto.ContactDTO;
import com.alkemy.ong.exception.ContactException;
import com.alkemy.ong.service.ContactService;

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

    @PostMapping
    public ResponseEntity<?> createContact(@Validated @RequestBody ContactDTO contactDTO) throws ContactException, IOException{
        try{
            ContactDTO contact = contactService.save(contactDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(contact);
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
		
		//if(contactsDTO.isEmpty()) {
		//	response.put("Error","Contact list is empty.");
		//	return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		//}
		
		response.put("ok", contactsDTO.get());
		return ResponseEntity.ok(response);
	}
	
}
