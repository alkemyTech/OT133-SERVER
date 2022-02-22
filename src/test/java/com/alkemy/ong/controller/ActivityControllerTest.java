package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.ActivityService;
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

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
public class ActivityControllerTest {
  private static final String USER_CREDENTIALS = "user@mail.com";
  private static final String ADMIN_CREDENTIALS = "agustinleyes@alkemy.org";
  private static final String route = "/activities";
  @Autowired
  ActivityController activityController;
  private String baseUrl;
  private ActivityDTO activityDTO;
  private ObjectMapper objectMapper;
  @MockBean
  private ActivityRepository activityRepository;
  @MockBean
  private ActivityService activityService;
  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    activityDTO = new ActivityDTO();
    activityDTO.setName("name test activity");
    activityDTO.setContent("content test activity");
    activityDTO.setImage("image test activity");
    baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
    objectMapper = new ObjectMapper();

  }

  // --------------------------------------------------------------------------------------------
  // Post
  // --------------------------------------------------------------------------------------------
  @Test
  void whenPost_notLoggedIn_thenUnauthorized() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(activityDTO))
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void whenPost_noAdmin_thenForbidden() throws Exception {
    mockMvc.perform(post(route).content(getJSON(activityDTO))
      .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_Admin_thenConflictInternal() throws Exception {
    when(activityService.createActivity(activityDTO)).thenThrow(ConstraintViolationException.class);
    mockMvc.perform(post(route).content(getJSON(activityDTO))
      .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isConflict());
  }

  @Test
  @Transactional
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_aValidDTO_then_isCreated() throws Exception {
    Mockito.when(activityService.createActivity(activityDTO)).thenReturn(activityDTO);

    mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(activityDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().string(getJSON(activityDTO)));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_withNameNull_then_isBadRequest() throws Exception {

    activityDTO.setName(null);

    mockMvc.perform(post(route).content(getJSON(activityDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_withNameEmpty_then_isBadRequest() throws Exception {

    activityDTO.setName("");

    mockMvc.perform(post(route).content(getJSON(activityDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_withNameBlank_then_isBadRequest() throws Exception {

    activityDTO.setName("                                                ");

    mockMvc.perform(post(route).content(getJSON(activityDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_withContentNull_then_isBadRequest() throws Exception {

    activityDTO.setContent(null);

    mockMvc.perform(post(route).content(getJSON(activityDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_withContentEmpty_then_isBadRequest() throws Exception {

    activityDTO.setContent("");

    mockMvc.perform(post(route).content(getJSON(activityDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_withContentBlank_then_isBadRequest() throws Exception {

    activityDTO.setContent("                 ");

    mockMvc.perform(post(route).content(getJSON(activityDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_withImageNull_then_isBadRequest() throws Exception {

    activityDTO.setImage(null);

    mockMvc.perform(post(route).content(getJSON(activityDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_withImageEmpty_then_isBadRequest() throws Exception {

    activityDTO.setImage("");

    mockMvc.perform(post(route).content(getJSON(activityDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_withImageBlank_then_isBadRequest() throws Exception {

    activityDTO.setImage("                 ");

    mockMvc.perform(post(route).content(getJSON(activityDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  // --------------------------------------------------------------------------------------------
  // Put
  // --------------------------------------------------------------------------------------------

  @Test
  void whenPut_notLoggedIn_then_isUnauthorized() throws Exception {
    String givenId = "a-test-ID";

    mockMvc.perform(put(String.format("%s/%s", route, givenId))
        .content(getJSON(activityDTO)).contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void whenPut_noAdmin_then_isForbidden() throws Exception {
    String givenId = "a-test-ID";

    mockMvc.perform(put(String.format("%s/%s", route, givenId))
        .content(getJSON(activityDTO)).contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_and_doesNotExistsId_then_isNotFound() throws Exception {

    String givenId = "a-test-ID";
    when(activityService.findById(givenId)).thenReturn(null);

    mockMvc.perform(put(String.format("%s/%s", route, givenId))
        .content(getJSON(activityDTO)).contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_and_doesNotExists_then_EntityNotFoundException() throws Exception {

    String givenId = "a-test-ID";
    when(activityService.findById(givenId)).thenReturn(activityDTO);
    when(activityService.updateActivity(givenId, activityDTO)).thenThrow(EntityNotFoundException.class);

    mockMvc.perform(put(String.format("%s/%s", route, givenId))
        .content(getJSON(activityDTO)).contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isConflict());
  }

  @Test
  @Transactional
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_aValidDTO_then_isOK() throws Exception {

    String id = "195c57c0-bae4-44c3-9ab9-16088637d743";
    Mockito.when(activityService.findById(id)).thenReturn(activityDTO);
    Mockito.when(activityService.updateActivity(id, activityDTO)).thenReturn(activityDTO);

    mockMvc.perform(put(String.format("%s/%s", route, id)).content(getJSON(activityDTO))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.content().string(getJSON(activityDTO)));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_withNameNull_then_isBadRequest() throws Exception {

    activityDTO.setName(null);

    mockMvc.perform(put(String.format("%s/%s", route, "ID-ABC"))
        .content(getJSON(activityDTO)).contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_withNameBlank_then_isBadRequest() throws Exception {

    activityDTO.setName("                       ");

    mockMvc.perform(put(String.format("%s/%s", route, "ID-ABC"))
        .content(getJSON(activityDTO)).contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_withNameEmpty_then_isBadRequest() throws Exception {

    activityDTO.setName("");

    mockMvc.perform(put(String.format("%s/%s", route, "ID-ABC"))
        .content(getJSON(activityDTO)).contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_withContentNull_then_isBadRequest() throws Exception {

    activityDTO.setContent(null);

    mockMvc.perform(put(String.format("%s/%s", route, "ID-ABC"))
        .content(getJSON(activityDTO)).contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_withContentEmpty_then_isBadRequest() throws Exception {

    activityDTO.setContent("");

    mockMvc.perform(put(String.format("%s/%s", route, "ID-ABC"))
        .content(getJSON(activityDTO)).contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_withContentBlank_then_isBadRequest() throws Exception {

    activityDTO.setContent("           ");

    mockMvc.perform(put(String.format("%s/%s", route, "ID-ABC"))
        .content(getJSON(activityDTO)).contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_withImageNull_then_isBadRequest() throws Exception {

    activityDTO.setImage(null);

    mockMvc.perform(put(String.format("%s/%s", route, "ID-ABC"))
        .content(getJSON(activityDTO)).contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_withImageEmpty_then_isBadRequest() throws Exception {

    activityDTO.setImage("");

    mockMvc.perform(put(String.format("%s/%s", route, "ID-ABC"))
        .content(getJSON(activityDTO)).contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_withImageBlank_then_isBadRequest() throws Exception {

    activityDTO.setImage("           ");

    mockMvc.perform(put(String.format("%s/%s", route, "ID-ABC"))
        .content(getJSON(activityDTO)).contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  // --------------------------------------------------------------------------------------------
  // Internal Methods
  // --------------------------------------------------------------------------------------------

  private String getJSON(ActivityDTO activityDTO) throws JsonProcessingException {
    return objectMapper.writeValueAsString(activityDTO);
  }

}
