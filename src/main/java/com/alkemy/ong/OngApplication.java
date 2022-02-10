package com.alkemy.ong;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import com.alkemy.ong.entity.Category;
import com.alkemy.ong.entity.Comment;
import com.alkemy.ong.entity.Contact;
import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.entity.Slide;
import com.alkemy.ong.entity.member.Member;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.SlideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OngApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(OngApplication.class, args);
  }

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
  @Autowired CommentRepository commentRepository;

  @Override
  public void run(String... args) throws Exception {
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
    
    // Comment
    createCommentIfNotExists("NoBodyA");
    createCommentIfNotExists("NoBodyB");
    
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

  @Transactional
  private void createCommentIfNotExists(String body) {

    Comment comment = commentRepository.findByBody(body);

    if (Objects.isNull(comment)) {
      comment = new Comment();
      comment.setBody(body);
      commentRepository.save(comment);
    }

  }

}
