package com.alkemy.ong.config;

import com.alkemy.ong.security.UserDetailServiceImpl;
import com.alkemy.ong.security.filter.TokenAuthenticationFilter;
import com.alkemy.ong.security.filter.TokenAuthorizationFilter;
import com.alkemy.ong.security.token.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String[] AUTH_WHITELIST = {
      // -- Swagger UI v2
      "/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
      "/configuration/security", "/swagger-ui.html", "/webjars/**",
      // -- Swagger UI v3 (OpenAPI)
      "/v3/api-docs/**", "/swagger-ui/**",
      // other public endpoints of your API may be appended to this array
      "/auth/**"};


  @Autowired
  private UserDetailServiceImpl userDetailsService;

  @Value("${security.token.validator-secret}")
  private String tokenSecret;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    TokenValidator tokenValidator = new TokenValidator(tokenSecret);

    // Set Filter URL
    TokenAuthenticationFilter tokenAuthenticationFilter =
        new TokenAuthenticationFilter(authenticationManagerBean(), tokenValidator);
    tokenAuthenticationFilter.setFilterProcessesUrl("/auth/login");

    // Habilita CORS y desactiva CSRF
    http.cors().and().csrf().disable();

    // Seteo de session management a stateless
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Permitir todo en /**/auth/**
    http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll().anyRequest()
        // El Resto de las rutas, requeriran autenticación
        .authenticated();

    // Add JWT Token Authorization filter
    http.addFilterBefore(new TokenAuthorizationFilter(tokenValidator),
        UsernamePasswordAuthenticationFilter.class);
    // Add JWT Token Authentication filter
    http.addFilter(tokenAuthenticationFilter);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authProvider());
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(encoder());
    return authProvider;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
