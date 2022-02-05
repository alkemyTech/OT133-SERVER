package com.alkemy.ong.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import com.alkemy.ong.entity.Slide;
import com.alkemy.ong.service.SlideService;

public class SlideControllerTest {
	
	@InjectMocks
	SlideController slideController;
	
	@Mock
	private SlideService slideService;
	
	private Slide slide1;
	private Slide slide2;
	
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
	}
	
	@Test
	public void getSlide() throws IOException{
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		when(slideService.findAllDefined()).thenReturn(Arrays.asList(slide1, slide2));
		
		assertNotNull(slideController.findAllDefined());
	}
	
}
