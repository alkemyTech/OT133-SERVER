package com.alkemy.ong.service;

import java.util.*;

import com.alkemy.ong.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repo;

    @Autowired
    private RolService roleService;
  
    public User crearUser(String firstName, String lastName, String email, String password,
    String photo, /* Integer roleId */ String roleName){
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        if(photo != null)
            user.setPhoto(photo);
        user.setRole(roleService.buscarPorRoleName(roleName));
        repo.save(user);
        return user;
    }

    public User login(String email, String password) {

        User u = buscarPorEmail(email);
        
        if (u == null || !u.getPassword().equals(Crypto.encrypt(password, u.getEmail().toLowerCase()))) {
            throw new BadCredentialsException("Usuario o contraseña invalida");
        }
        
        return u;
    }

    public User buscarPorEmail(String email){
        return repo.findByEmail(email);
    }
    
    public UserDetails getUserAsUserDetail(User user) {
        
        UserDetails uDetails;

        uDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    
        return uDetails;
    }

    Set < ? extends GrantedAuthority > getAuthorities(User user) {

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        String role = user.getRole().getName();

        authorities.add(new SimpleGrantedAuthority("CLAIM_ROLE_" + role));

        authorities.add(new SimpleGrantedAuthority("CLAIM_entityId_" + user.getId()));
        
        return authorities;
    }

    public Map<String, String> getUserClaims(User user) {
        
        Map<String, String> claims = new HashMap<>();
    
        claims.put("userRole", user.getRole().getName());
        claims.put("userId", user.getId());
    
        return claims;
    }

    /*  @Configuration
        @EnableWebSecurity
        @EnableGlobalMethodSecurity(prePostEnabled = true)  
        
        en WebSecurityConfig

        - - - - - -

        @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")

        en endponits / controllers
    */
}
