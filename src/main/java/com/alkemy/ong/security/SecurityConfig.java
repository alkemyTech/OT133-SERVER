package com.alkemy.ong.security;

import com.alkemy.ong.security.filter.TokenAuthenticationFilter;
import com.alkemy.ong.security.filter.TokenAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private UserDetailServiceImpl userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Set Filter URL
		TokenAuthenticationFilter tokenAuthenticationFilter =
				new TokenAuthenticationFilter(authenticationManagerBean());
		tokenAuthenticationFilter.setFilterProcessesUrl("/auth/login");

		// Habilita CORS y desactiva CSRF
		http.cors().and().csrf().disable();

		// Seteo de session management a stateless
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Permitir todo en /**/auth/**
		http.authorizeRequests().antMatchers("/auth/**").permitAll().anyRequest()
				// El Resto de las rutas, requeriran autenticación
				.authenticated();

		// Add JWT Token Authorization filter
		http.addFilterBefore(new TokenAuthorizationFilter(),
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
