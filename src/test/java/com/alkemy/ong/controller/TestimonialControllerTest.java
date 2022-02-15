package com.alkemy.ong.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Arrays;
import java.util.List;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.impl.TestimonialServiceImpl;
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

  // Testimonial endpoint.
  private static final String route = "/testimonials";

  // Base URL from current context path.
  private String baseUrl;

  @MockBean
  private TestimonialRepository testimonialRepository;

  @MockBean
  private TestimonialServiceImpl testimonialService;

  @Autowired
  TestimonialController testimonialController;

  @Autowired
  private MockMvc mockMvc;

  TestimonialDTO dto;

  @BeforeEach
  void setUp() {
    dto = new TestimonialDTO();
    dto.setName("A valid name");
    dto.setContent("A valid content");

    baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
  }

  // --------------------------------------------------------------------------------------------
  // Get
  // --------------------------------------------------------------------------------------------

  @Test
  @Transactional
  void whenGet_andNotLoggedIn_thenUnauthorized() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(route))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  @WithUserDetails("admin@alkemy.org")
  void whenGets_andAdminLoggedIn_thenOk() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(route))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @WithUserDetails("user@mail.com")
  void whenGets_andUserLoggedIn_thenOk() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(route))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @WithUserDetails("admin@alkemy.org")
  void whenGet_aValidPage_thenOk() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get(route.concat("?page=0")))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @WithUserDetails("admin@alkemy.org")
  void wheGet_anInvalidPage_thenIllegalArgumentException() throws Exception {

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
  @WithUserDetails("admin@alkemy.org")
  void wheGet_aValidPage_and_notZero_then_containsPrevious_and_isOk() throws Exception {

    Integer page = 1;

    mockMvc.perform(MockMvcRequestBuilders.get(String.format("%s?page=%d", route, page)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(
            String.format("\"previous\":\"%s%s?page=%d\"", baseUrl, route, page - 1))));
  }

  @Test
  @WithUserDetails("admin@alkemy.org")
  void wheGet_aValidPage_and_hasNext_then_containsNext_and_isOk() throws Exception {

    Integer page = 1;

    List<TestimonialDTO> notAnEmptyList = Arrays.asList(dto, dto, dto);
    Mockito.when(testimonialService.read(page + 1)).thenReturn(notAnEmptyList);

    mockMvc.perform(MockMvcRequestBuilders.get(String.format("%s?page=%d", route, page)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().string(Matchers
            .containsString(String.format("\"next\":\"%s%s?page=%d\"", baseUrl, route, page + 1))));
  }

}
