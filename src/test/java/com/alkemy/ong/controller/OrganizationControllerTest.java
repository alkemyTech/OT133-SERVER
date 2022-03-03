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


import java.util.*;

import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.OrganizationService;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrganizationControllerTest {

  private static final String ADMIN_CREDENTIALS = "maurodell@alkemy.org";

  private static final String route = "/organization/public";

  private static final String route2 = "/organization/public/all";

  @Autowired
  OrganizationController organizationController;

  private String baseUrl;

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

  /***
   * Get
   */

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void readAllDefinedTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(route))
      .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenGets_andAdminLoggedIn_thenOk() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(route2))
      .andExpect(MockMvcResultMatchers.status().isOk());
  }


  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenGet_AllContact_thenBadRequest() throws Exception {

    when(organizationService.readAll()).thenThrow(java.lang.ClassCastException.class);

    mockMvc.perform(MockMvcRequestBuilders.get(route2))
      .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  /***
   * Post
   */
  @Test
  @Transactional
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_Admin_aValid_then_isCreated() throws Exception {
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
  void whenPost_withPhoneIncorrect_then_isBadRequest() throws Exception {

    organization.setPhone(-1);

    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(organization))
      .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());

  }


  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_withEmailExist_then_isBadRequest() throws Exception {

    organization.setEmail(ADMIN_CREDENTIALS);

    when(organizationService.existsByEmail(ADMIN_CREDENTIALS)).thenReturn(true);
    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(organization))
      .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());

  }






  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_withUpdate_then_isOk() throws Exception {

    organization.setEmail(ADMIN_CREDENTIALS);

    mockMvc.perform(MockMvcRequestBuilders.put(route+"/{email}",ADMIN_CREDENTIALS).content(getJSON(organization))
      .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());

  }
  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_withMessageEmpty_then_isBadRequest74asd56asasdasa() throws Exception {


    when(organizationService.findByEmail(organization.getEmail())).thenReturn(Optional.of(organization));

    mockMvc.perform(MockMvcRequestBuilders.put(route+"/{email}",organization.getEmail()).content(getJSON(organization))
      .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_withExceptionEmail_then_isNotFound() throws Exception {


    when(organizationService.findByEmail(ADMIN_CREDENTIALS)).thenThrow(RuntimeException.class);


    mockMvc.perform(MockMvcRequestBuilders.put(route+"/{email}",ADMIN_CREDENTIALS).content(getJSON(organization))
      .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());
  }


  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whendelete_withEmailNotPresent_then_isNotFound() throws Exception {

    organization.setEmail(ADMIN_CREDENTIALS);

    mockMvc.perform(MockMvcRequestBuilders.put(route+"/delete/{email}",ADMIN_CREDENTIALS).content(getJSON(organization))
      .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());

  }


  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whendelete_withDelete_then_isOk() throws Exception {


    when(organizationService.findByEmail(organization.getEmail())).thenReturn(Optional.of(organization));

    mockMvc.perform(MockMvcRequestBuilders.put(route+"/delete/{email}",organization.getEmail()).content(getJSON(organization))
      .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated());

  }



  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPosdelete() throws Exception {


    when(organizationService.findByEmail(ADMIN_CREDENTIALS)).thenThrow(RuntimeException.class);


    mockMvc.perform(MockMvcRequestBuilders.put(route+"/delete/{email}",ADMIN_CREDENTIALS).content(getJSON(organization))
      .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());
  }





  /***
   * Internal Methods
   */
  private String getJSON(Organization organization) throws JsonProcessingException {
    return objectMapper.writeValueAsString(organization);
  }

}
