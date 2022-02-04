package com.alkemy.ong.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.alkemy.ong.entity.User;
import com.alkemy.ong.service.UserDAO;
import com.alkemy.ong.service.UserService;

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
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = this.userService.getByEmail(username).orElse(null);

    if (Objects.isNull(user)) {
      throw new UsernameNotFoundException("No user found with username: " + username);
    }

    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;

    // Se agregan los roles del usuario.
    List<GrantedAuthority> authorities = user.getRoles()
			.stream()
			.map(rol -> new SimpleGrantedAuthority(rol.getName().name()))
			.collect(Collectors.toList());

    return new org.springframework.security.core.userdetails.User(user.getEmail(),
        user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
        authorities);
  }

}
