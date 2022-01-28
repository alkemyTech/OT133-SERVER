package com.alkemy.ong.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.service.OrganizationService;

@RestController
@RequestMapping("/auth/organization")
public class OrganizationController {
	@Autowired
	private OrganizationService organizationService;
	
	
	@GetMapping("/public")
	public ResponseEntity<List<Organization>> readAllDefined(){
		    List<Organization> organization = StreamSupport
		    		.stream(organizationService.readAllDefined().spliterator(), false)
		    		.collect(Collectors.toList());
		    
		    return new ResponseEntity<>(organization, HttpStatus.OK);
	}
	
	@PostMapping("/public")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> create(@Valid @RequestBody Organization organization){
		if(organizationService.existsByEmail(organization.getEmail())) {
			return new ResponseEntity("el email ya existe",HttpStatus.BAD_REQUEST);
		}
		if(!validationNumber(organization.getPhone())) {
			return new ResponseEntity("solo debe ingresar números",HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(organizationService.save(organization));
	}
	
	public static boolean validationNumber(Integer numb) {
		return numb.toString().matches("[0-9]*");
	}
}
