package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activities")
public class ActivityController {
	@Autowired
	private ActivityService activityService;

  @PreAuthorize("hasAuthority('ROL_ADMIN')")
	@PostMapping
	public ResponseEntity<ActivityDTO> createActivity(@Validated @RequestBody ActivityDTO request){
		try {
			ActivityDTO response = activityService.createActivity(request);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			System.out.println(e);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		
		}

	}

	@PreAuthorize("hasAuthority('ROL_ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateActivity(@PathVariable String id, @Validated @RequestBody ActivityDTO activity){
		try{
			  if(activityService.findById(id) != null){
				ActivityDTO activityUpdated = activityService.updateActivity(id, activity);
				return ResponseEntity.ok(activityUpdated);
			  } else { 
			  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activity not found by the given id");
			  }
		} catch (Exception e){
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Exception happened: " + e.getLocalizedMessage());
		} 
	}
}
