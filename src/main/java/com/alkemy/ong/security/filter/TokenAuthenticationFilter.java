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
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Filtro de autenticación de token JWT.
 * 
 * @author Tomás Sánchez
 * @since 1.0
 */
public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final int TEN_MINUTES = 10 * 60 * 1_000;

    private final AuthenticationManager authenticationManager;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getParameter("email"), request.getParameter("password"));

        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain, Authentication authentication)
            throws IOException, ServletException {

        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        String issuer = request.getRequestURL().toString();
        TokenValidator tokenValidator = new TokenValidator();

        // Generating Tokens
        String accesToken =
                tokenValidator.generateTokenForUser(user, TEN_MINUTES * 3, issuer, true);
        String refreshToken =
                tokenValidator.generateTokenForUser(user, TEN_MINUTES * 6, issuer, false);


        Map<String, String> tokens = new HashMap<String, String>();
        tokens.put("access_token", accesToken);
        tokens.put("refresh_token", refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
