package com.alkemy.ong.config;

import com.alkemy.ong.security.UserDetailServiceImpl;
import com.alkemy.ong.security.filter.TokenAuthenticationFilter;
import com.alkemy.ong.security.filter.TokenAuthorizationFilter;
import com.alkemy.ong.security.token.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import org.springframework.security.web.savedrequest.NullRequestCache;

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

  private static final String[] USER_POST = {"/contacts/**"};

  @Autowired
  private UserDetailServiceImpl userDetailsService;

  @Value("${security.token.validator-secret}")
  private String tokenSecret;

  @Autowired
  private RESTAuthenticationEntryPoint authenticationEntryPoint;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    try {
      TokenValidator tokenValidator = new TokenValidator(tokenSecret);

      // Set Filter URL
      TokenAuthenticationFilter tokenAuthenticationFilter =
          new TokenAuthenticationFilter(authenticationManagerBean(), tokenValidator);
      tokenAuthenticationFilter.setFilterProcessesUrl("/auth/login");

      // Stateless session
      http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

      // Habilita CORS y desactiva CSRF
      http.cors().and().csrf().disable();

      // Permitir todo en WHITELIST
      http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
          .antMatchers(HttpMethod.GET, "/**").hasAnyAuthority("ROL_ADMIN", "ROL_USER")
          .antMatchers(HttpMethod.POST, USER_POST).hasAnyAuthority("ROL_ADMIN", "ROL_USER")
          .antMatchers(HttpMethod.POST, "/**").hasAuthority("ROL_ADMIN")
          .antMatchers(HttpMethod.PUT, "/**").hasAuthority("ROL_ADMIN")
          .antMatchers(HttpMethod.DELETE, "/**").hasAuthority("ROL_ADMIN").anyRequest()
          .authenticated().and().requestCache().requestCache(new NullRequestCache()).and()
          .httpBasic().authenticationEntryPoint(authenticationEntryPoint);

      // http.authorizeRequests().anyRequest().authenticated();

      // Add JWT Token Authorization filter
      http.addFilterBefore(new TokenAuthorizationFilter(tokenValidator),
          UsernamePasswordAuthenticationFilter.class);

      // Add JWT Token Authentication filter
      http.addFilter(tokenAuthenticationFilter);


    } catch (Exception ex) {
      ex.getMessage();
    }

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
