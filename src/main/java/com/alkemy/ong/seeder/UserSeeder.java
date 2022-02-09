package com.alkemy.ong.seeder;

import java.util.Arrays;
import java.util.Objects;
import javax.transaction.Transactional;
import com.alkemy.ong.entity.Rol;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.enums.Roles;
import com.alkemy.ong.repository.RolRepository;
import com.alkemy.ong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder implements CommandLineRunner {

  private static final String ALKEMY = "@alkemy.org";
  private static final String MAIL = "@mail.com";

  @Autowired
  UserRepository userRepository;

  @Autowired
  private RolRepository rolRepository;

  @Override
  public void run(String... args) throws Exception {

    // Roles
    createRolIfNotExists(Roles.ROL_ADMIN, "User with all privileges and authorities");
    createRolIfNotExists(Roles.ROL_USER, "User with no privileges nor authorities");

    // Admin Users
    createUserIfNotExists("null", "null", "admin".concat(ALKEMY), "admin");
    createUserIfNotExists("Juan", "Lopez", "admin", Roles.ROL_ADMIN);
    createUserIfNotExists("Agustin", "Leyes", "admin", Roles.ROL_ADMIN);
    createUserIfNotExists("Tomas", "Sanchez", "admin", Roles.ROL_ADMIN);
    createUserIfNotExists("Juan", "Rodriguez", "admin", Roles.ROL_ADMIN);
    createUserIfNotExists("Mauro", "Dell", "admin", Roles.ROL_ADMIN);
    createUserIfNotExists("Muriel", "Correa", "admin", Roles.ROL_ADMIN);
    createUserIfNotExists("Ricardo", "Ledesma", "admin", Roles.ROL_ADMIN);
    createUserIfNotExists("Rodrigo", "Caro", "admin", Roles.ROL_ADMIN);
    createUserIfNotExists("null", "mull", "alkemy".concat(ALKEMY), "admin");

    // Normal Users
    createUserIfNotExists("null", "null", "user".concat(MAIL), "user");
    createUserIfNotExists("Juan", "Lopez", "user", Roles.ROL_USER);
    createUserIfNotExists("Agustin", "Leyes", "user", Roles.ROL_USER);
    createUserIfNotExists("Tomas", "Sanchez", "user", Roles.ROL_USER);
    createUserIfNotExists("Juan", "Rodriguez", "user", Roles.ROL_USER);
    createUserIfNotExists("Mauro", "Dell", "user", Roles.ROL_USER);
    createUserIfNotExists("Muriel", "Correa", "user", Roles.ROL_USER);
    createUserIfNotExists("Ricardo", "Ledesma", "user", Roles.ROL_USER);
    createUserIfNotExists("Rodrigo", "Caro", "user", Roles.ROL_USER);
    createUserIfNotExists("null", "mull", "alkemy".concat(MAIL), "user");

  }


  @Transactional
  private Rol createRolIfNotExists(Roles roleName, String description) {

    Rol rol = rolRepository.findByName(roleName);

    if (Objects.isNull(rol)) {
      rol = rolRepository.save(new Rol(roleName, description));
    }

    return rol;
  }

  private User createUserIfNotExists(String name, String lastName, String password, Roles rol) {
    return createUserIfNotExists(name, lastName,
        name.concat(lastName).concat(rol.equals(Roles.ROL_ADMIN) ? ALKEMY : MAIL).toLowerCase(),
        password);
  }

  @Transactional
  private User createUserIfNotExists(String name, String lastName, String email, String password) {

    User user = userRepository.findByEmail(email).orElseGet(() -> {

      User newUser = new User();

      newUser.setEmail(email);
      newUser.setPassword(new BCryptPasswordEncoder().encode(password));
      newUser.setFirstName(name);
      newUser.setLastName(lastName);
      newUser.setRoles(Arrays.asList(
          rolRepository.findByName(email.contains(ALKEMY) ? Roles.ROL_ADMIN : Roles.ROL_USER)));

      return userRepository.save(newUser);
    });

    return user;
  }

}
