package com.alkemy.ong.controller;

import java.util.List;
import javax.validation.Valid;
import com.alkemy.ong.dto.OrganizationPublicDTO;
import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("organization")
public class OrganizationController {
  @Autowired
  private OrganizationService organizationService;

  @GetMapping("/public")
  public ResponseEntity<List<OrganizationPublicDTO>> readAllDefined() {
    List<OrganizationPublicDTO> organizationPublicDTOS = organizationService.readAllDefined();
    return new ResponseEntity<>(organizationPublicDTOS, HttpStatus.OK);
  }

  @PostMapping("/public")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
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
}
