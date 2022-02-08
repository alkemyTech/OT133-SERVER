package com.alkemy.ong;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import com.alkemy.ong.entity.Category;
import com.alkemy.ong.entity.Contact;
import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.entity.Rol;
import com.alkemy.ong.entity.Slide;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.entity.member.Member;
import com.alkemy.ong.enums.Roles;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.RolRepository;
import com.alkemy.ong.repository.SlideRepository;
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
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private OrganizationRepository organizationRepository;
  @Autowired
  private SlideRepository slideRepository;
  @Autowired
  private ContactRepository contactRepository;

  @Override
  public void run(String... args) throws Exception {

    // Roles
    createRolIfNotExists(Roles.ROL_ADMIN, "User with all privileges and authorities");
    createRolIfNotExists(Roles.ROL_USER, "User with no privileges nor authorities");

    // Users
    createAdminIfNotExists("admin@alkemy.org", "admin");

    // Categories
    createCategoryIfNotExists("A");
    createCategoryIfNotExists("B");

    // Members
    createMemberIfNotExists("A");
    createMemberIfNotExists("B");

    // Organization
    createOrgIfNotExists("NoNameA");
    createOrgIfNotExists("NoNameB");

    // Slides
    createSlideIfNotExists("NoNameA");
    createSlideIfNotExists("NoNameA");
    createSlideIfNotExists("NoNameB");
    createSlideIfNotExists("NoNameB");
    createSlideIfNotExists("NoNameB");

    if (contactRepository.count() == 0) {
      Contact contactOne = new Contact();
      contactOne.setEmail("primerContact@mail.com");
      contactOne.setMessage("Welcome first contact");
      contactOne.setPhone(433233445L);
      contactOne.setName("Jon Doe");
      contactOne.setTimestamps(LocalDateTime.now());

      Contact contactTwo = new Contact();
      contactTwo.setEmail("segundoContact@mail.com");
      contactTwo.setMessage("Welcome second contact");
      contactTwo.setPhone(40003456L);
      contactTwo.setName("Juan Diaz");
      contactTwo.setTimestamps(LocalDateTime.now());

      this.contactRepository.save(contactOne);
      this.contactRepository.save(contactTwo);
    }

  }

  @Transactional
  private Rol createRolIfNotExists(Roles roleName, String description) {

    Rol rol = rolRepository.findByName(roleName);

    if (Objects.isNull(rol)) {
      rol = rolRepository.save(new Rol(roleName, description));
    }

    return rol;
  }

  @Transactional
  private User createAdminIfNotExists(String email, String password) {

    User user = userRepository.findByEmail(email).orElseGet(() -> {

      User newUser = new User();
      newUser.setEmail(email);
      newUser.setPassword(new BCryptPasswordEncoder().encode(password));
      newUser.setFirstName("Admin");
      newUser.setLastName("Admin");
      newUser.setRoles(Arrays.asList(rolRepository.findByName(Roles.ROL_ADMIN)));

      return userRepository.save(newUser);
    });

    return user;
  }

  @Transactional
  private void createCategoryIfNotExists(String name) {

    Category category = categoryRepository.findByName(name);

    if (Objects.isNull(category)) {
      category = new Category();
      category.setName(name);
      categoryRepository.save(category);
    }

  }

  @Transactional
  private void createMemberIfNotExists(String name) {
    Member member = memberRepository.findByName(name);

    if (Objects.isNull(member)) {
      member = new Member();
      member.setName(name);
      member.setImage(name.concat(".png"));
      memberRepository.save(member);
    }

  }

  @Transactional
  private void createOrgIfNotExists(String name) {
    Organization org = organizationRepository.findByName(name);

    if (Objects.isNull(org)) {
      org = new Organization();
      org.setName(name);
      org.setImage(name.concat(".png"));
      org.setWelcomeText(String.format("Welcome to %s!", name));
      org.setEmail(name.concat("@mail.com"));
      organizationRepository.save(org);
    }

  }

  @Transactional
  private void createSlideIfNotExists(String orgName) {
    Organization org = organizationRepository.findByName(orgName);

    List<Slide> slides = slideRepository.findAllByOrganization(org);

    if (Objects.nonNull(org) && slides.size() < 3) {
      Slide slide = new Slide();
      slide.setImageUrl(String.format("%s-%d.png", orgName, slides.size()));
      slide.setOrderNumber(slides.size() + 1);
      slide.setOrganization(org);
      slide.setText(String.format("Text of slide no. %d", slides.size()));
      slideRepository.save(slide);
    }

  }

}
