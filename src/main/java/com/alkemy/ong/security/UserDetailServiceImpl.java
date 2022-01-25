package com.alkemy.ong.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alkemy.ong.entity.User;
import com.alkemy.ong.service.UserDAO;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private UserDAO userDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userDAO.getByEmail(username).get();
		return new org.springframework.security.core.userdetails.User(user.getEmail()
				,user.getPassword(),
				new ArrayList<>());
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}

}
