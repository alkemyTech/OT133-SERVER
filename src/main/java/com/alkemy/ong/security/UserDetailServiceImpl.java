package com.alkemy.ong.security;

import java.util.ArrayList;
import java.util.Objects;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.service.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
		User user = this.userDAO.getByEmail(username).orElse(null);

		if (Objects.isNull(user)) {
			throw new UsernameNotFoundException("No user found with username: " + username);
		}

		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		User user = this.userDAO.getByEmail(username).get();
		
		//Se agregan los roles del usuario.
		List<GrantedAuthority> authorities = new ArrayList<>();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRoleId().getName().name().toString());
		authorities.add(authority);
    
		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
	}
  
}
