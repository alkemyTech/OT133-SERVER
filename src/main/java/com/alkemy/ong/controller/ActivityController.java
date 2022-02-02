package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activities")
public class ActivityController {
	@Autowired
	private ActivityService activityService;

	@PostMapping
	public ResponseEntity<ActivityDTO> createActivity(@RequestBody ActivityDTO request){
		try {
		activityService.verifyActivity(request);
			ActivityDTO response = activityService.createActivity(request);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			System.out.println(e);
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
		

	}
}
}
