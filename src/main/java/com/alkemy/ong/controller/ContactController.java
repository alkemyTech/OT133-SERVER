package com.alkemy.ong.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.dto.ContactDTO;
import com.alkemy.ong.service.ContactService;

@RestController
@RequestMapping("/contacts")
public class ContactController {

	@Autowired
	private ContactService contactService;
	
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
