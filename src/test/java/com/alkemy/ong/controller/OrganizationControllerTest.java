package com.alkemy.ong.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolationException;

import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.OrganizationService;

@SpringBootTest
@AutoConfigureMockMvc
public class OrganizationControllerTest {
	
	private static final String USER_CREDENTIALS = "user@mail.com";

	  private static final String ADMIN_CREDENTIALS = "maurodell@alkemy.org";

	  private static final String route = "/organization/public";
	  
	  private static final String route2 = "/organization/public/all";
	  
	  @Autowired
	  OrganizationController organizationController;
	  
	  private String baseUrl;
	  private OrganizationDTO organizationDTO;
	  private Organization organization;
	  
	  private ObjectMapper objectMapper;
	  @MockBean
	  private OrganizationRepository organizationRepository;
	  @MockBean
	  private OrganizationService organizationService;
	  @Autowired
	  private MockMvc mockMvc;

	  @BeforeEach
	  void setUp() {
		  organizationDTO = new OrganizationDTO();
		  organizationDTO.setName("Fuerza");
		  organizationDTO.setEmail("fuerza@gmail.com");
		  organizationDTO.setPhone(2365236);
		  organizationDTO.setImage("img/nueva.jpg");
		  organizationDTO.setAddress("Callao 325");
		  organizationDTO.setAboutUsText("Sobre nosotros");
		  organizationDTO.setWelcomeText("Bienvenidos");
		  
		  organization = new Organization();
		  organization.setName("Fuerza");
		  organization.setEmail("fuerza@gmail.com");
		  organization.setPhone(2365236);
		  organization.setImage("img/nueva.jpg");
		  organization.setAddress("Callao 325");
		  organization.setAboutUsText("Sobre nosotros");
		  organization.setWelcomeText("Bienvenidos");

	    
		  baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
		  objectMapper = new ObjectMapper();
	  }

	  // --------------------------------------------------------------------------------------------
	  // Get
	  // --------------------------------------------------------------------------------------------

	  @Test
	  void whenGet_andNotLoggedIn_thenUnauthorized() throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.get(route2))
	    .andExpect(MockMvcResultMatchers.status().isUnauthorized());
	  }

	  @Test
	  @WithUserDetails(ADMIN_CREDENTIALS)
	  void whenGets_andAdminLoggedIn_thenOk() throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.get(route2))
	    .andExpect(MockMvcResultMatchers.status().isOk());
	  }

	  @Test
	  @WithUserDetails(ADMIN_CREDENTIALS)
	  void whenGets_andUserLoggedIn_thenForbidden() throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.get(route2))
	    .andExpect(MockMvcResultMatchers.status().isForbidden());
	  }

	  @Test
	  @WithUserDetails(ADMIN_CREDENTIALS)
	  void whenGet_AllContact_thenOk() throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.get(route2))
	      .andExpect(MockMvcResultMatchers.status().isOk());
	  }

	  // --------------------------------------------------------------------------------------------
	  // Post
	  // --------------------------------------------------------------------------------------------

	  @Test
	  void whenPost_notLoggedIn_thenUnauthorized() throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(organization))
	      .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isUnauthorized());
	  }

	  @Test
	  void whenPost_User_thenConflictInternal() throws Exception {

	    Mockito.when(organizationService.save(organization)).thenThrow(ConstraintViolationException.class);
	    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(organization))
	      .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isConflict());
	  }

	  @Test
	  @Transactional
	  @WithUserDetails(ADMIN_CREDENTIALS)
	  void whenPost_User_aValidDTO_then_isCreated() throws Exception {
	    Mockito.when(organizationService.save(organization)).thenReturn(organization);

	    mockMvc
	      .perform(MockMvcRequestBuilders.post(route).content(getJSON(organization))
	        .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(MockMvcResultMatchers.status().isCreated())
	      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	      .andExpect(MockMvcResultMatchers.content().string(getJSON(organization)));
	  }

	  @Test
	  @WithUserDetails(ADMIN_CREDENTIALS)
	  void whenPost_withNameNull_then_isBadRequest() throws Exception {

  		organization.setName(null);

	    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(organization))
	        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
	      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	  }

	  @Test
	  @WithUserDetails(ADMIN_CREDENTIALS)
	  void whenPost_withNameEmpty_then_isBadRequest() throws Exception {

		organization.setName("");

	    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(organization))
	        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
	      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	  }

	  @Test
	  @WithUserDetails(ADMIN_CREDENTIALS)
	  void whenPost_withNameBlank_then_isBadRequest() throws Exception {

		organization.setName("                                                ");

	    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(organization))
	        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
	      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	  }

	  @Test
	  @WithUserDetails(ADMIN_CREDENTIALS)
	  void whenPost_withEmailNull_then_isBadRequest() throws Exception {

		organization.setEmail(null);

	    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(organization))
	        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
	      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	  }

	  @Test
	  @WithUserDetails(ADMIN_CREDENTIALS)
	  void whenPost_withEmailEmpty_then_isBadRequest() throws Exception {

		  organization.setEmail("");

	    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(organization))
	        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
	      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	  }

	  @Test
	  @WithUserDetails(ADMIN_CREDENTIALS)
	  void whenPost_withEmailBlank_then_isBadRequest() throws Exception {

		  organization.setEmail("                 ");

	    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(organization))
	        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
	      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	  }

	  @Test
	  @WithUserDetails(ADMIN_CREDENTIALS)
	  void whenPost_withPhoneNull_then_isBadRequest() throws Exception {

		  organization.setPhone(-1);

	    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(organization))
	        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
	      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	  }

	  @Test
	  @WithUserDetails(ADMIN_CREDENTIALS)
	  void whenPost_withMessageNull_then_isBadRequest() throws Exception {

		  organization.setImage(null);

	    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(organization))
	        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
	      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	  }

	  @Test
	  @WithUserDetails(ADMIN_CREDENTIALS)
	  void whenPost_withMessageEmpty_then_isBadRequest() throws Exception {

		  organization.setImage("");

	    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(organization))
	        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
	      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	  }

	  @Test
	  @WithUserDetails(ADMIN_CREDENTIALS)
	  void whenPost_withMessageBlank_then_isBadRequest() throws Exception {

		  organization.setImage("                 ");

	    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(organization))
	        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
	      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	  }

	  // --------------------------------------------------------------------------------------------
	  // Internal Methods
	  // --------------------------------------------------------------------------------------------

	  private String getJSON(Organization organization) throws JsonProcessingException {
	    return objectMapper.writeValueAsString(organization);
	  }
	  
}
