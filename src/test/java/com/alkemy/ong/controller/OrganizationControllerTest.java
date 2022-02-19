package com.alkemy.ong.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.service.OrganizationService;


public class OrganizationControllerTest {
	
	@InjectMocks
	OrganizationController organizationController;
	
	@Mock
	private OrganizationService organizationService;
	
	private Organization ong;

	private OrganizationDTO ongDTO;
	
	@BeforeEach
	public void init() {		
		MockitoAnnotations.openMocks(this);
		
		ong = new Organization();
		ong.setName("Todos juntos podemos");
		ong.setEmail("todos@gmail.com");
		ong.setAddress("Av. Roca 123");
		ong.setImage("img/nueva.jpg");
		ong.setPhone(341256983);
		ong.setWelcomeText("Bienvenidos a Todos Juntos...");
		ong.setAboutUsText("Sobre nosotros...");
		ong.setId("1");
		
		ongDTO = new OrganizationDTO();
		ongDTO.setName("Todos juntos podemos");
		ongDTO.setEmail("todos@gmail.com");
		ongDTO.setAddress("Av. Roca 123");
		ongDTO.setImage("img/nueva.jpg");
		ongDTO.setPhone(341256983);
		ongDTO.setWelcomeText("Bienvenidos a Todos Juntos...");
		ongDTO.setAboutUsText("Sobre nosotros...");
	}
	
	@Test
	public void organizationCreateTest() throws IOException{
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		when(organizationService.save(any(Organization.class))).thenReturn(ong);
		
		assertNotNull(organizationController.create(ong));
	}
	
	@Test
	public void updateTest() throws IOException{
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		Optional<Organization> ongUpdate = Optional.ofNullable(new Organization());
		
		Organization change = ongUpdate.get();
		change.setEmail("mauro@gmail.com");
		change.setName("Nombre Viejo");
		
		when(organizationService.save(any(Organization.class))).thenReturn(change);
		
		assertNotNull(organizationController.update("mauro@gmail.com", ong));
	}
	
	@Test
	public void deleteTest() throws IOException{
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		Optional<Organization> ongUpdate = Optional.ofNullable(new Organization());
		
		Organization change = ongUpdate.get();
		change.setSoftDelete(false);
		
		when(organizationService.save(any(Organization.class))).thenReturn(change);
		
		assertNotNull(organizationController.update("mauro@gmail.com", ong));
	}
	
	@Test
	public void findAllTest() {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		
		List<OrganizationDTO> ongList = organizationService.readAllDefined();
		ongList.add(ongDTO);
		
		assertTrue(ongList.size() == 1);
	}
}
