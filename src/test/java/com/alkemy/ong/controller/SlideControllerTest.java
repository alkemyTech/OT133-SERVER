package com.alkemy.ong.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.entity.Slide;
import com.alkemy.ong.service.SlideService;
import com.amazonaws.services.apigateway.model.BadRequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class SlideControllerTest {

	private static final String ROL_USER = "user@mail.com";
    private static final String ROL_ADMIN = "admin@alkemy.org";
	
	@Autowired
	SlideController slideController;
	
	@MockBean
	private SlideService slideService;

	@Autowired
	private MockMvc mockMvc;
	
    private ObjectMapper objectMapper;

	private Slide slide1;
	private Slide slide2;
	private SlideDTO dto;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		
		slide1 = new Slide();
		slide1.setImageUrl("/img/somosmas1.jpg");
		slide1.setOrderNumber(1);
		slide1.setText("Imagen de bienvenida");
		
		slide2 = new Slide();
		slide2.setImageUrl("/img/somosmas1.jpg");
		slide2.setOrderNumber(1);
		slide2.setText("Imagen de bienvenida");

		dto = new SlideDTO();
		dto.setImageUrl("/img/somosmas1.jpg");
		dto.setOrderNumber(1);
		dto.setText("text");

		this.objectMapper = new ObjectMapper();
	}

	// Get

	@Test
	void getNotLoggedIn() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/slides" + "/{id}", 1))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithUserDetails(ROL_USER)
	void getByUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/slides" + "/{id}", 1))
		.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithUserDetails(ROL_ADMIN)
	public void getSlideIdNull() throws Exception {
		String id = "abc123";
		dto.setText(null);
		Mockito.when(slideService.findById(id)).thenReturn(dto);
		mockMvc.perform(MockMvcRequestBuilders.get("/slides" + "/{id}", id))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	@WithUserDetails(ROL_ADMIN)
	public void getSlideOk() throws Exception {
		String id = "abc123";
		Mockito.when(slideService.findById(id)).thenReturn(dto);
		mockMvc.perform(MockMvcRequestBuilders.get("/slides" + "/{id}", id))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	////////////////////////////////////////////

	@Test
	void findAllNotLoggedIn() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/slides"))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithUserDetails(ROL_USER)
	void findAllByUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/slides"))
		.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithUserDetails(ROL_ADMIN)
	void findAllException() throws Exception {
		
		Mockito.doThrow(BadRequestException.class).when(slideService).findAllDefined();
		mockMvc.perform(MockMvcRequestBuilders.get("/slides"))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	@WithUserDetails(ROL_ADMIN)
	void findAll() throws Exception {
		
		when(slideService.findAllDefined()).thenReturn(Arrays.asList(slide1, slide2));
		mockMvc.perform(MockMvcRequestBuilders.get("/slides"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithUserDetails(ROL_ADMIN)
	public void getSlide() throws IOException{
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		when(slideService.findAllDefined()).thenReturn(Arrays.asList(slide1, slide2));
		
		assertNotNull(slideController.findAllDefined());
	}

	// Post

	@Test
	void postNotLoggedIn() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/slides"))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithUserDetails(ROL_USER)
	void postByUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/slides"))
		.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithUserDetails(ROL_ADMIN)
	void postException() throws Exception {
		
		Mockito.doThrow(Exception.class).when(slideService).saveSlide(dto);
		mockMvc.perform(MockMvcRequestBuilders.post("/slides")
		.content(getJSON(dto)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotAcceptable());
	}

	@Test
	@WithUserDetails(ROL_ADMIN)
	void postByAdmin() throws Exception {
		
		when(slideService.saveSlide(dto)).thenReturn(dto);
		mockMvc.perform(MockMvcRequestBuilders.post("/slides")
        .content(getJSON(dto)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	// Put

	@Test
	void putNotLoggedIn() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/slides"))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithUserDetails(ROL_USER)
	void putByUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/slides"))
		.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithUserDetails(ROL_ADMIN)
	void putSlideNotFound() throws Exception {
		UUID id = UUID.randomUUID();
		when(slideService.update(dto, id.toString())).thenReturn(Optional.empty());
		mockMvc.perform(MockMvcRequestBuilders.put("/slides" + "/{id}", id)
        .content(getJSON(dto)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@WithUserDetails(ROL_ADMIN)
	void putSlideByAdmin() throws Exception {
		UUID id = UUID.randomUUID();
		when(slideService.update(dto, id.toString())).thenReturn(Optional.of(dto));
		mockMvc.perform(MockMvcRequestBuilders.put("/slides" + "/{id}", id)
        .content(getJSON(dto)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	// Delete

	@Test
	void deleteNotLoggedIn() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/slides"))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithUserDetails(ROL_USER)
	void deleteByUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/slides"))
		.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithUserDetails(ROL_ADMIN)
	void deleteException() throws Exception {
		String id = "abc123";
		Mockito.doThrow(Exception.class).when(slideService).deleteSlide(id);
		mockMvc.perform(MockMvcRequestBuilders.delete("/slides" + "/{id}", id))
		.andExpect(MockMvcResultMatchers.status().isOk());

	}

	////////////////

	private String getJSON(SlideDTO slideDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(slideDTO);
    }
}
