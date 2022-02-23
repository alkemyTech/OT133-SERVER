
package com.alkemy.ong.controller;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.exception.NewException;
import com.alkemy.ong.repository.NewsRepository;

import com.alkemy.ong.service.NewService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityNotFoundException;


import com.alkemy.ong.service.impl.NewServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.hibernate.sql.Delete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.NestedServletException;

@SpringBootTest
@AutoConfigureMockMvc
public class NewControllerTest {

	// User with ROL_USER
	private static final String USER_CREDENTIALS = "user@mail.com";

	// User with ROL_ADMIN
	private static final String ADMIN_CREDENTIALS = "admin@alkemy.org";

	// Testimonial endpoint.
	private static final String route = "/news";

	// Base URL from current context path.
	private String baseUrl;

	// DTO for tests
	private NewDTO dto;

	// Jackson mapper to obtain JSON comparissons
	private ObjectMapper objectMapper;

	@MockBean
	private NewsRepository newsRepository;

	@MockBean
	private NewServiceImpl newService;

	@Autowired
	NewController newController;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		dto = new NewDTO();
		dto.setName("A valid name");
		dto.setContent("A valid content");

		baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
		objectMapper = new ObjectMapper();
	}

	// --------------------------------------------------------------------------------------------
	// Get
	// --------------------------------------------------------------------------------------------

	@Test
	void whenGet_andNotLoggedIn_thenUnauthorized() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(route)).andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}




	// --------------------------------------------------------------------------------------------
	// Update
	// --------------------------------------------------------------------------------------------

	@Test
	void whenPut_notLoggedIn_then_isUnauthorized() throws Exception {

		String givenId = "a-test-ID";

		mockMvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, givenId)).content(getJSON(dto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithUserDetails(USER_CREDENTIALS)
	void whenPut_noAdmin_then_isForbidden() throws Exception {

		String givenId = "a-test-ID";

		mockMvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, givenId)).content(getJSON(dto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPut_and_doesNotExists_then_isNotFound() throws Exception {

		
		dto.setId("1");
		Mockito.when(newService.update(dto, dto.getId())).thenThrow(NewException.class);

		mockMvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, dto.getId())).content(getJSON(dto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@Transactional
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPut_aValidDTO_then_isOK() throws Exception {

		String givenId = "a-test-ID";
		dto.setId("1");
		Mockito.when(newService.update(dto, dto.getId())).thenReturn(dto);

		mockMvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, dto.getId())).content(getJSON(dto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string(getJSON(dto)));
	}


	// --------------------------------------------------------------------------------------------
	// Delete
	// --------------------------------------------------------------------------------------------

	@Test
	void whenDelete_notLoggedIn_then_isUnauthorized() throws Exception {

		String givenId = "a-test-ID";

		mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", route, givenId)))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithUserDetails(USER_CREDENTIALS)
	void whenDelete_notAdmin_then_isForbidden() throws Exception {

		String givenId = "a-test-ID";

		mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", route, givenId)))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@Transactional
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenDelete_and_doesNotExist_then_isNotFound() throws Exception {

		String givenId = "a-test-ID";

		Mockito.doThrow(EntityNotFoundException.class).when(newService).delete(givenId);
		mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", route, givenId)))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@Transactional
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenDelete_and_exists_then_isOk() throws Exception {
//		String givenId = "a-test-ID";
//
//		Mockito.doThrow(EntityNotFoundException.class).when(newService).delete(givenId);
//		mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", route, givenId)))
//				.andExpect(MockMvcResultMatchers.status().isOk());
//  
		String givenId = "a-test-ID";
	    mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", route, givenId)))
	        .andExpect(MockMvcResultMatchers.status().isOk());
	
	}

	// --------------------------------------------------------------------------------------------
	// Internal Methods
	// --------------------------------------------------------------------------------------------

	private String getJSON(NewDTO dto) throws JsonProcessingException {
		return objectMapper.writeValueAsString(dto);
	}

}