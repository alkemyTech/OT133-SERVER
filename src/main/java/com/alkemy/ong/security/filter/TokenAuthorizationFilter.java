package com.alkemy.ong.security.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alkemy.ong.security.token.TokenValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filtro de authorización con token JWT
 * 
 * @author Tomás Sánchez
 * @since 1.0
 */
public class TokenAuthorizationFilter extends OncePerRequestFilter {

  private static final String TOKEN_PREFIX = "Bearer ";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    // Saltear cuando es el /login
    if (request.getServletPath().contains("auth/login")) {
      filterChain.doFilter(request, response);
    } else {
      String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

      if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
        try {
          SecurityContextHolder.getContext()
              .setAuthentication(new TokenValidator().retrieveUserAuthTokenFromHeader(authHeader));
          filterChain.doFilter(request, response);
        } catch (Exception e) {

          response.setHeader("error", e.getMessage());
          response.setStatus(HttpStatus.UNAUTHORIZED.value());
          Map<String, String> errors = new HashMap<>();
          errors.put("error_message", e.getMessage());
          new ObjectMapper().writeValue(response.getOutputStream(), errors);
        }
      } else {
        filterChain.doFilter(request, response);
      }
    }
  }

}
