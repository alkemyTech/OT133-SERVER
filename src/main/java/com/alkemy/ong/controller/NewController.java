package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.service.NewService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("news")
public class NewController {

	@Autowired
	NewService newService;

	@PostMapping
	@PreAuthorize("ROL_ADMIN")
	public ResponseEntity<NewDTO> save(NewDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(newService.save(dto));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable UUID id) {
		Map<String, Object> response = new HashMap<>();
		Optional<NewDTO> newDTO = this.newService.getById(id.toString());
		if (newDTO.isEmpty()) {
			response.put("NotFound", String.format("New with ID %s not found", id.toString()));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			response.put("ok", newDTO.get());
			return ResponseEntity.ok(response);
		}
	}
}
