package com.alkemy.ong.controller;

import java.util.Collections;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.security.UserDetailServiceImpl;
import com.alkemy.ong.security.exception.UserAlreadyExistsException;
import com.alkemy.ong.security.payload.SignupRequest;
import com.alkemy.ong.service.UserDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest extends BaseControllerTest {

  private static final String EXAMPLE_MAIL = "example@mail.com";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private UserDAO userDAO;

  @MockBean
  private AuthenticationManager authenticationManager;

  @MockBean
  private UserDetailServiceImpl uds;

  @Autowired
  AuthController authController;

  @MockBean
  BCryptPasswordEncoder encoder;

  // The Auth DTO
  private SignupRequest dto;

  protected String route = "/auth";

  @BeforeEach
  void setUp() {
    dto = new SignupRequest();
    dto.setEmail(EXAMPLE_MAIL);
    dto.setPassword("example");
    dto.setFirstname("Name");
    dto.setLastname("Last name");
    baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
    objectMapper = new ObjectMapper();
  }

  // --------------------------------------------------------------------------------------------
  // Register
  // --------------------------------------------------------------------------------------------

  @Test
  @Transactional
  void whenRegister_aValidDTO_then_isCreated() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(String.format("%s/%s", route, "register"))
            .content(getDTO(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  void whenRegister_withNullEmail_then_isBadRequest() throws Exception {

    dto.setEmail(null);

    mockMvc
        .perform(MockMvcRequestBuilders.post(String.format("%s/%s", route, "register"))
            .content(getDTO(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void whenRegister_withEmptyEmail_then_isBadRequest() throws Exception {

    dto.setEmail("");

    mockMvc
        .perform(MockMvcRequestBuilders.post(String.format("%s/%s", route, "register"))
            .content(getDTO(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void whenRegister_withBlankEmail_then_isBadRequest() throws Exception {
    dto.setEmail("                                                 ");
    mockMvc
        .perform(MockMvcRequestBuilders.post(String.format("%s/%s", route, "register"))
            .content(getDTO(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  @Transactional
  void whenRegister_withExistentEmail_then_isConflict() throws Exception {
    User user = new User();
    user.setEmail(dto.getEmail());
    user.setPassword(dto.getPassword());
    user.setFirstName(dto.getFirstname());
    user.setLastName(dto.getLastname());

    Mockito.when(userDAO.create(user)).thenThrow(UserAlreadyExistsException.class);

    mockMvc
        .perform(MockMvcRequestBuilders.post(String.format("%s/%s", route, "register"))
            .content(getDTO(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isConflict());
  }


  @Test
  void whenRegister_withInvalidEmail_then_isBadRequest() throws Exception {
    dto.setEmail("not-A-Mail");
    mockMvc
        .perform(MockMvcRequestBuilders.post(String.format("%s/%s", route, "register"))
            .content(getDTO(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }


  @Test
  void whenRegister_withNullPassword_then_isBadRequest() throws Exception {
    dto.setPassword(null);
    mockMvc
        .perform(MockMvcRequestBuilders.post(String.format("%s/%s", route, "register"))
            .content(getDTO(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void whenRegister_withEmptyPassword_then_isBadRequest() throws Exception {
    dto.setPassword("");
    mockMvc
        .perform(MockMvcRequestBuilders.post(String.format("%s/%s", route, "register"))
            .content(getDTO(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void whenRegister_withBlankPassword_then_isBadRequest() throws Exception {
    dto.setPassword("                                                     ");
    mockMvc
        .perform(MockMvcRequestBuilders.post(String.format("%s/%s", route, "register"))
            .content(getDTO(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void whenRegister_withNullFirstName_then_isBadRequest() throws Exception {
    dto.setFirstname(null);
    mockMvc
        .perform(MockMvcRequestBuilders.post(String.format("%s/%s", route, "register"))
            .content(getDTO(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void whenRegister_withEmptyFirstName_then_isBadRequest() throws Exception {
    dto.setFirstname("");
    mockMvc
        .perform(MockMvcRequestBuilders.post(String.format("%s/%s", route, "register"))
            .content(getDTO(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void whenRegister_withBlankFirstName_then_isBadRequest() throws Exception {
    dto.setFirstname("                                                      ");
    mockMvc
        .perform(MockMvcRequestBuilders.post(String.format("%s/%s", route, "register"))
            .content(getDTO(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void whenRegister_withNullLastName_then_isBadRequest() throws Exception {
    dto.setLastname(null);
    mockMvc
        .perform(MockMvcRequestBuilders.post(String.format("%s/%s", route, "register"))
            .content(getDTO(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void whenRegister_withEmptyLastName_then_isBadRequest() throws Exception {
    dto.setLastname("");
    mockMvc
        .perform(MockMvcRequestBuilders.post(String.format("%s/%s", route, "register"))
            .content(getDTO(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void whenRegister_withBlankLastName_then_isBadRequest() throws Exception {
    dto.setLastname("                                                   ");
    mockMvc
        .perform(MockMvcRequestBuilders.post(String.format("%s/%s", route, "register"))
            .content(getDTO(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  // --------------------------------------------------------------------------------------------
  // Log In
  // --------------------------------------------------------------------------------------------

  @Test
  void whenLogIn_withCredentials_then_isOK() throws Exception {

    String password = "password";
    String form = String.format("email=%s&password=%s", EXAMPLE_MAIL, password);

    Mockito.when(uds.loadUserByUsername(EXAMPLE_MAIL))
        .thenReturn(new org.springframework.security.core.userdetails.User(EXAMPLE_MAIL, password,
            true, true, true, true, Collections.emptyList()));

    Mockito.when(encoder.matches(password, password)).thenReturn(true);

    mockMvc
        .perform(MockMvcRequestBuilders.post(String.format("%s/%s", route, "login")).content(form)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(MockMvcResultMatchers.status().isOk());

  }

}
