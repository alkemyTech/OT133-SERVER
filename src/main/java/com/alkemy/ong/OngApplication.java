package com.alkemy.ong;

import com.alkemy.ong.entity.*;
import com.alkemy.ong.entity.member.Member;
import com.alkemy.ong.enums.Roles;
import com.alkemy.ong.repository.*;

import java.time.LocalDateTime;

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
    if (memberRepository.count() == 0) {
      // Creacion de member
      Member member = new Member();
      member.setName("member");
      member.setImage("member.jpg");
      memberRepository.save(member);

      Member member1 = new Member();
      member1.setName("member1");
      member1.setImage("member1.jpg");
      memberRepository.save(member1);
    }
    if (organizationRepository.count() == 0) {
      // Creacion de organization
      Organization organization = new Organization();
      organization.setName("organizacionA");
      organization.setWelcomeText("Bienvenido A");
      organization.setEmail("organizacionA@gmail");
      organization.setImage("organizacionA.png");
      organizationRepository.save(organization);

      Organization organization1 = new Organization();
      organization1.setName("organizacionB");
      organization1.setWelcomeText("Bienvenido A");
      organization1.setEmail("organizacionB@gmail");
      organization1.setImage("organizacionb.png");
      organizationRepository.save(organization1);
      
    }

    if (slideRepository.count() == 0) {
      String id1="552bb2e2-a17e-4ae4-9964-e0272479107d";
      String id2="ebea63aa-b556-481f-82d9-64ec3e949fa1";
      // Creacion de Slide
      Slide slide = new Slide();
      slide.setImageUrl("slide1.png");
      slide.setOrderNumber(1);
      slide.setOrganization(organizationRepository.findById(id1).get());
      slide.setText("Texto de slide 1");
      slideRepository.save(slide);

      // Creacion de Slide
      Slide slide5 = new Slide();
      slide5.setImageUrl("slide2.png");
      slide5.setOrderNumber(2);
      slide5.setOrganization(organizationRepository.findById(id1).get());
      slide5.setText("Texto de slide 2");
      slideRepository.save(slide5);
      
///////////////////////////////////////////////////////////////
      Slide slide1 = new Slide();
      slide1.setImageUrl("slide4.png");
      slide1.setOrderNumber(7);
      slide1.setOrganization(organizationRepository.findById(id2).get());
      slide1.setText("Texto de slide 4");
      slideRepository.save(slide1);
      
      Slide slide2 = new Slide();
      slide2.setImageUrl("slide5.png");
      slide2.setOrderNumber(42);
      slide2.setOrganization(organizationRepository.findById(id2).get());
      slide2.setText("Texto de slide 5");
      slideRepository.save(slide2);

      Slide slide3 = new Slide();
      slide3.setImageUrl("slide6.png");
      slide3.setOrderNumber(1);
      slide3.setOrganization(organizationRepository.findById(id2).get());
      slide3.setText("Texto de slide 6");
      slideRepository.save(slide3);
      
      
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

}
