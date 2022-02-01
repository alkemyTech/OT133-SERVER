package com.alkemy.ong;

import java.util.Arrays;
import javax.transaction.Transactional;
import com.alkemy.ong.entity.Category;
import com.alkemy.ong.entity.Rol;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.enums.Roles;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.RolRepository;
import com.alkemy.ong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class OngApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(OngApplication.class, args);
  }

  @Autowired
  private RolRepository rolRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CategoryRepository categoryRepository;


  @Override
  public void run(String... args) throws Exception {

    createRolIfNotExists(Roles.ROL_ADMIN, "User with admin privileges");
    createRolIfNotExists(Roles.ROL_USER, "User with no privileges");
    createUserIfNotExists();

    if (categoryRepository.count() == 0) {
      // Creacion de categoria
      Category category = new Category();
      category.setName("categoria");
      categoryRepository.save(category);
    }


  }

  @Transactional
  private User createUserIfNotExists() {
    User admin = userRepository.findByEmail("admin@alkemy.org").orElse(null);

    if (admin == null) {
      admin = new User();
      admin.setEmail("admin@alkemy.org");
      admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
      admin.setFirstName("Admin");
      admin.setLastName("Admin");
      admin.setRoleId(Arrays.asList(rolRepository.findByName(Roles.ROL_ADMIN)));
      userRepository.save(admin);
    }

    return admin;
  }


  @Transactional
  private Rol createRolIfNotExists(Roles rolName, String description) {

    Rol rol = rolRepository.findByName(rolName);

    if (rol == null) {
      // Creacion rol Admin
      rol = new Rol();
      rol.setName(rolName);
      rol.setDescription(description);
      rolRepository.save(rol);
    }

    return rol;
  }


}
