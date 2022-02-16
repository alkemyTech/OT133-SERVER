package com.alkemy.ong.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.dto.TestimonialIDDTO;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.impl.TestimonialServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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
import org.springframework.web.util.NestedServletException;

@SpringBootTest
@AutoConfigureMockMvc
public class TestimonialControllerTest {

  // User with ROL_USER
  private static final String USER_CREDENTIALS = "user@mail.com";

  // User with ROL_ADMIN
  private static final String ADMIN_CREDENTIALS = "admin@alkemy.org";

  // Testimonial endpoint.
  private static final String route = "/testimonials";

  // Base URL from current context path.
  private String baseUrl;

  // DTO for tests
  private TestimonialDTO dto;

  // Jackson mapper to obtain JSON comparissons
  private ObjectMapper objectMapper;

  @MockBean
  private TestimonialRepository testimonialRepository;

  @MockBean
  private TestimonialServiceImpl testimonialService;

  @Autowired
  TestimonialController testimonialController;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    dto = new TestimonialDTO();
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
    mockMvc.perform(MockMvcRequestBuilders.get(route))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenGets_andAdminLoggedIn_thenOk() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(route))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void whenGets_andUserLoggedIn_thenOk() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(route))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenGet_aValidPage_thenOk() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get(route.concat("?page=0")))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenGet_anInvalidPage_thenIllegalArgumentException() throws Exception {

    // An invalid page is less than zero (0).
    Integer invalidPage = -1;
    Mockito.when(testimonialService.read(invalidPage)).thenCallRealMethod();

    assertThrows(IllegalArgumentException.class, () -> {
      try {
        mockMvc
            .perform(MockMvcRequestBuilders.get(String.format("%s?page=%d", route, invalidPage)));
      } catch (NestedServletException e) {
        throw e.getCause();
      }
    });

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenGet_aValidPage_and_notZero_then_containsPrevious_and_isOk() throws Exception {

    Integer page = 1;

    mockMvc.perform(MockMvcRequestBuilders.get(String.format("%s?page=%d", route, page)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(
            String.format("\"previous\":\"%s%s?page=%d\"", baseUrl, route, page - 1))));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenGet_aValidPage_and_hasNext_then_containsNext_and_isOk() throws Exception {

    Integer page = 1;

    List<TestimonialDTO> notAnEmptyList = Arrays.asList(dto, dto, dto);
    Mockito.when(testimonialService.read(page + 1)).thenReturn(notAnEmptyList);

    mockMvc.perform(MockMvcRequestBuilders.get(String.format("%s?page=%d", route, page)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().string(Matchers
            .containsString(String.format("\"next\":\"%s%s?page=%d\"", baseUrl, route, page + 1))));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenGet_aValidPage_and_doesNot_haveNext_then_doesNot_ContainsNext_and_isOk()
      throws Exception {

    Map<String, Object> expected = new HashMap<>();
    expected.put("content", Collections.EMPTY_LIST);

    mockMvc.perform(MockMvcRequestBuilders.get(String.format("%s?page=%d", route, 0)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expected)));
  }

  // --------------------------------------------------------------------------------------------
  // Post
  // --------------------------------------------------------------------------------------------

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_withNameNull_then_isBadRequest() throws Exception {

    dto.setName(null);

    mockMvc
        .perform(MockMvcRequestBuilders.post(route).content(getJSON(dto))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_withNameEmpty_then_isBadRequest() throws Exception {

    dto.setName("");

    mockMvc
        .perform(MockMvcRequestBuilders.post(route).content(getJSON(dto))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_withNameBlank_then_isBadRequest() throws Exception {

    dto.setName("                                                ");

    mockMvc
        .perform(MockMvcRequestBuilders.post(route).content(getJSON(dto))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_withContentNull_then_isBadRequest() throws Exception {

    dto.setContent(null);

    mockMvc
        .perform(MockMvcRequestBuilders.post(route).content(getJSON(dto))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_withContentEmpty_then_isBadRequest() throws Exception {

    dto.setContent("");

    mockMvc
        .perform(MockMvcRequestBuilders.post(route).content(getJSON(dto))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPost_withContentBlank_then_isBadRequest() throws Exception {

    dto.setContent("                               ");

    mockMvc
        .perform(MockMvcRequestBuilders.post(route).content(getJSON(dto))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void whenPost_notLoggedIn_thenUnauthorized() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(route).content(getJSON(dto))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void whenPost_noAdmin_thenForbidden() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(route).content(getJSON(dto))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  @Transactional
  void whenPost_aValidDTO_then_isCreated() throws Exception {

    String givenId = "a-test-ID";
    Mockito.when(testimonialService.create(dto)).thenReturn(new TestimonialIDDTO(givenId, dto));

    mockMvc
        .perform(MockMvcRequestBuilders.post(route).content(getJSON(dto))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().string(getJSON(dto)))
        .andExpect(MockMvcResultMatchers.header().string("location",
            String.format("%s%s/%s", baseUrl, route, givenId)));
  }

  // --------------------------------------------------------------------------------------------
  // Update
  // --------------------------------------------------------------------------------------------

  @Test
  void whenPut_notLoggedIn_then_isUnauthorized() throws Exception {

    String givenId = "a-test-ID";

    mockMvc
        .perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, givenId))
            .content(getJSON(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void whenPut_noAdmin_then_isForbidden() throws Exception {

    String givenId = "a-test-ID";

    mockMvc
        .perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, givenId))
            .content(getJSON(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_and_doesNotExists_then_isNotFound() throws Exception {

    String givenId = "a-test-ID";

    Mockito.when(testimonialService.update(givenId, dto)).thenThrow(EntityNotFoundException.class);

    mockMvc
        .perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, givenId))
            .content(getJSON(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @Transactional
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_aValidDTO_then_isOK() throws Exception {

    String givenId = "a-test-ID";

    Mockito.when(testimonialService.update(givenId, dto)).thenReturn(dto);

    mockMvc
        .perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, givenId))
            .content(getJSON(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().string(getJSON(dto)));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_withNameNull_then_isBadRequest() throws Exception {

    dto.setName(null);

    mockMvc
        .perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, "ID-ABC"))
            .content(getJSON(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_withNameEmpty_then_isBadRequest() throws Exception {

    dto.setName("");

    mockMvc
        .perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, "ID-ABC"))
            .content(getJSON(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_withNameBlank_then_isBadRequest() throws Exception {

    dto.setName("                                                 ");

    mockMvc
        .perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, "ID-ABC"))
            .content(getJSON(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_withContentNull_then_isBadRequest() throws Exception {

    dto.setContent(null);

    mockMvc
        .perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, "ID-ABC"))
            .content(getJSON(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_withContentEmpty_then_isBadRequest() throws Exception {

    dto.setContent("");

    mockMvc
        .perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, "ID-ABC"))
            .content(getJSON(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenPut_withContentBlank_then_isBadRequest() throws Exception {

    dto.setContent("                                                 ");

    mockMvc
        .perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, "ID-ABC"))
            .content(getJSON(dto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
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

    Mockito.doThrow(EntityNotFoundException.class).when(testimonialService).delete(givenId);
    mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", route, givenId)))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @Transactional
  @WithUserDetails(ADMIN_CREDENTIALS)
  void whenDelete_and_exists_then_isOk() throws Exception {

    String givenId = "a-test-ID";
    mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", route, givenId)))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  // --------------------------------------------------------------------------------------------
  // Internal Methods
  // --------------------------------------------------------------------------------------------

  private String getJSON(TestimonialDTO dto) throws JsonProcessingException {
    return objectMapper.writeValueAsString(dto);
  }

}
