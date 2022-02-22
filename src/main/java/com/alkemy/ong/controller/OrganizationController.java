package com.alkemy.ong.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import com.alkemy.ong.dto.OrganizationPublicDTO;
import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.service.OrganizationService;
import com.amazonaws.services.apigateway.model.BadRequestException;
import com.amazonaws.services.apigateway.model.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organization")
public class OrganizationController {
  @Autowired
  private OrganizationService organizationService;

  @GetMapping("/public/social-media")
  public ResponseEntity<List<OrganizationPublicDTO>> readAllDefinedPublic() {
    List<OrganizationPublicDTO> organizationPublicDTOS = organizationService.readAllDefinedPublic();
    return new ResponseEntity<>(organizationPublicDTOS, HttpStatus.OK);
  }

  @PostMapping("/public")
  public ResponseEntity<?> create(@Valid @RequestBody Organization organization) {
    if (organizationService.existsByEmail(organization.getEmail())) {
      return new ResponseEntity("el email ya existe", HttpStatus.BAD_REQUEST);
    }
    if (!validationNumber(organization.getPhone())) {
      return new ResponseEntity("solo debe ingresar números", HttpStatus.BAD_REQUEST);
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.save(organization));
  }

  public static boolean validationNumber(Integer numb) {
    return numb.toString().matches("[0-9]*");
  }
  @PutMapping("/public/{email}")
  public ResponseEntity<?> update(@PathVariable(name="email") String email, @RequestBody Organization organization) throws NotFoundException{
	  
	  try {
		  Optional<Organization> ong = organizationService.findByEmail(email);
		  if(!ong.isPresent()) {
			  return ResponseEntity.notFound().build();
		  }
		  
		  Organization change = ong.get();
		  change.setAboutUsText(organization.getAboutUsText());
		  change.setAddress(organization.getAddress());
		  change.setContact(organization.getContact());
		  change.setName(organization.getName());
		  change.setPhone(organization.getPhone());
		  change.setWelcomeText(organization.getWelcomeText());
		  
		  return ResponseEntity.status(HttpStatus.CREATED)
				  .body(organizationService.save(change));
	} catch (NotFoundException ex) {
		return new ResponseEntity(ex.getMessage() ,HttpStatus.NOT_FOUND);
	}

  }
  
  @PutMapping("/public/delete/{email}")
  public ResponseEntity<?> delete(@PathVariable(name="email") String email, @RequestBody Organization organization){
	  
	  try {
		  Optional<Organization> ong = organizationService.findByEmail(email);
		  if(!ong.isPresent()) {
			  return ResponseEntity.notFound().build();
		  }
		  
		  Organization change = ong.get();
		  change.setSoftDelete(false);
		  
		  return ResponseEntity.status(HttpStatus.CREATED)
				  .body(organizationService.save(change));
	} catch (NotFoundException ex) {
		return new ResponseEntity(ex.getMessage() ,HttpStatus.NOT_FOUND);
	}

  }
  @GetMapping("/public")
  public ResponseEntity<List<Organization>> readAllDefined() {
	  try {	
	    return new ResponseEntity(organizationService.readAllDefined(), HttpStatus.OK);
	} catch (BadRequestException ex) {
		return new ResponseEntity(ex.getMessage() ,HttpStatus.BAD_REQUEST);
	}
  }
}
