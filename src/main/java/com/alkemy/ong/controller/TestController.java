package com.alkemy.ong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.alkemy.ong.service.images.ImageUploaderService;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import org.apache.commons.io.FileUtils;

@RestController
@RequestMapping("/auth/test")
public class TestController {
	@Value("${cloud.aws.credentials.bucketName}")
	private String bucketName;
	@Autowired
	ImageUploaderService imageUploader;

	@GetMapping("/all")
	public String freeAccess() {
		return "Publico";
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('ROL_USER')")
	public String userAccess() {
		return "User";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ROL_ADMIN')")
	public String adminAccess() {
		return "Admin";
	}

	// /auth/test/upload
	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestPart(value = "file") MultipartFile file) throws IOException {

		String response = imageUploader.uploadImage(file);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/converse")
	public ResponseEntity<?> converseFile(@RequestPart(value = "file") String file) throws IOException {
		
		byte[] fileContent = FileUtils.readFileToByteArray(new File(file));
		String encodedString = Base64.getEncoder().encodeToString(fileContent);
		
		return ResponseEntity.status(HttpStatus.OK).body(encodedString);
	}
}
