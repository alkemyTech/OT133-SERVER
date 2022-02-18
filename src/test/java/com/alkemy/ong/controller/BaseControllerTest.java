package com.alkemy.ong.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseControllerTest {
  // User with ROL_USER
  protected static final String USER_CREDENTIALS = "user@mail.com";

  // User with ROL_ADMIN
  protected static final String ADMIN_CREDENTIALS = "admin@alkemy.org";

  // Testimonial endpoint.
  protected static String route = "/";

  // Base URL from current context path.
  protected String baseUrl;

  protected ObjectMapper objectMapper;

  protected String getDTO(Object obj) throws JsonProcessingException {
    return objectMapper.writeValueAsString(obj);
  }
}
