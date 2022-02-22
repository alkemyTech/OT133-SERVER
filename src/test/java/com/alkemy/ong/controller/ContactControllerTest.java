package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactDTO;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.service.ContactService;
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

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

  private static final String USER_CREDENTIALS = "user@mail.com";

  private static final String ADMIN_CREDENTIALS = "agustinleyes@alkemy.org";

  private static final String route = "/contacts";
  @Autowired
  ContactController contactController;
  private String baseUrl;
  private ContactDTO contactDTO;
  private ObjectMapper objectMapper;
  @MockBean
  private ContactRepository contactRepository;
  @MockBean
  private ContactService contactService;
  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    contactDTO = new ContactDTO();
    contactDTO.setName("name test contact");
    contactDTO.setEmail("email test contact");
    contactDTO.setPhone(Long.valueOf(1133448899));
    contactDTO.setMessage("message test contact");

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

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenGets_andAdminLoggedIn_thenOk() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(route)).andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void whenGets_andUserLoggedIn_thenForbidden() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(route)).andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenGet_AllContact_thenOk() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(route))
      .andExpect(MockMvcResultMatchers.status().isOk());
  }

  // --------------------------------------------------------------------------------------------
  // Post
  // --------------------------------------------------------------------------------------------

  @Test
  void whenPost_notLoggedIn_thenUnauthorized() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(contactDTO))
      .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void whenPost_User_thenConflictInternal() throws Exception {

    Mockito.when(contactService.save(contactDTO)).thenThrow(ConstraintViolationException.class);
    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(contactDTO))
      .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isConflict());
  }

  @Test
  @Transactional
  @WithUserDetails(USER_CREDENTIALS)
  void whenPost_User_aValidDTO_then_isCreated() throws Exception {
    Mockito.when(contactService.save(contactDTO)).thenReturn(contactDTO);

    mockMvc
      .perform(MockMvcRequestBuilders.post(route).content(getJSON(contactDTO))
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isCreated())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.content().string(getJSON(contactDTO)));
  }

  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void whenPost_withNameNull_then_isBadRequest() throws Exception {

    contactDTO.setName(null);

    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(contactDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void whenPost_withNameEmpty_then_isBadRequest() throws Exception {

    contactDTO.setName("");

    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(contactDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void whenPost_withNameBlank_then_isBadRequest() throws Exception {

    contactDTO.setName("                                                ");

    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(contactDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void whenPost_withEmailNull_then_isBadRequest() throws Exception {

    contactDTO.setEmail(null);

    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(contactDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void whenPost_withEmailEmpty_then_isBadRequest() throws Exception {

    contactDTO.setEmail("");

    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(contactDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void whenPost_withEmailBlank_then_isBadRequest() throws Exception {

    contactDTO.setEmail("                 ");

    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(contactDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void whenPost_withPhoneNull_then_isBadRequest() throws Exception {

    contactDTO.setPhone(null);

    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(contactDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void whenPost_withMessageNull_then_isBadRequest() throws Exception {

    contactDTO.setMessage(null);

    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(contactDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void whenPost_withMessageEmpty_then_isBadRequest() throws Exception {

    contactDTO.setMessage("");

    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(contactDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void whenPost_withMessageBlank_then_isBadRequest() throws Exception {

    contactDTO.setMessage("                 ");

    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(contactDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  // --------------------------------------------------------------------------------------------
  // Internal Methods
  // --------------------------------------------------------------------------------------------

  private String getJSON(ContactDTO contactDTO) throws JsonProcessingException {
    return objectMapper.writeValueAsString(contactDTO);
  }
}
