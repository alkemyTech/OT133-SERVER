package com.alkemy.ong;

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

    if (rolRepository.count() == 0) {
      // Creacion rol Admin
      Rol rolAdmin = new Rol();
      rolAdmin.setName(Roles.ROL_ADMIN);
      rolAdmin.setDescription("Usuario con privilegios de Administrador");
      rolRepository.save(rolAdmin);
      // Creacion de rol User
      Rol rolUser = new Rol();
      rolUser.setName(Roles.ROL_USER);
      rolUser.setDescription("Usuario sin ningun privilegio");
      rolRepository.save(rolUser);

    }

    if (userRepository.count() == 0) {
      User admin = new User();
      admin.setEmail("admin@alkemy.org");
      admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
      admin.setFirstName("Admin");
      admin.setLastName("Admin");
      userRepository.save(admin);
    }

    if (categoryRepository.count() == 0) {
      // Creacion de categoria
      Category category = new Category();
      category.setName("categoria");
      categoryRepository.save(category);
    }


  }

}
