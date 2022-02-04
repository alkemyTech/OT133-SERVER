package com.alkemy.ong.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.alkemy.ong.entity.contact.Contact;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "organizations")
@SQLDelete(sql = "UPDATE organizations SET soft_delete = true WHERE id=?")
@Where(clause = "soft_delete=false")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Organization extends PersistentEntity {

  private static final long serialVersionUID = 1L;

  @Column(name = "name", nullable = false, length = 100)
  @NotEmpty(message = "Name cannot be empty")
  @NotBlank
  @NotNull
  @Size(min = 4, message = "Name should be at least 4 characters")
  @Pattern(regexp = "[a-zA-Z]+", message = "Name must contain only letters")
  private String name;

  @Column(name = "image", nullable = false)
  @NotNull
  @NotEmpty(message = "An image must be loaded")
  @NotBlank
  private String image;

  @Column(name = "address", length = 100)
  private String address;

  @Column(name = "phone")
  private Integer phone;

  @Column(name = "email", nullable = false)
  @NotEmpty
  @NotNull
  @NotBlank
  @Email
  private String email;

  @Column(name = "welcomeText", nullable = false, length = 250)
  @NotEmpty
  @NotBlank
  private String welcomeText;

  @Column(name = "aboutUsText", length = 250)
  private String aboutUsText;

  @Embedded
  private Contact contact;
}
