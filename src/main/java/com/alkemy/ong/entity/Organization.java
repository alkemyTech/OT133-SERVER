package com.alkemy.ong.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.alkemy.ong.entity.contact.SocialLinks;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "organizations")
@SQLDelete(sql = "UPDATE organizations SET soft_delete = true WHERE id=?")
@FilterDef(name = "deletedOrganizationFilter",
    parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedOrganizationFilter", condition = "soft_delete = :isDeleted")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Organization extends PersistentEntity {

  private static final long serialVersionUID = 1L;

  @Column(name = "name", nullable = false, length = 100)
  @NotEmpty(message = "el nombre no puede estar vacío")
  @Size(min = 4, message = "el usuario debe tener al menos 4 caracteres")
  @Pattern(regexp = "[a-zA-Z]+", message = "ingresar solo letras")
  private String name;

  @Column(name = "image", nullable = false)
  @NotEmpty(message = "debe cargar una imagen")
  private String image;

  @Column(name = "address", length = 100)
  private String address;

  @Column(name = "phone")
  private Integer phone;

  @Column(name = "email", nullable = false)
  @NotEmpty(message = "debe ingresar email")
  @Email
  private String email;

  @Column(name = "welcomeText", nullable = false, length = 250)
  @NotEmpty
  private String welcomeText;

  @Embedded
  private SocialLinks contact;

  @Column(name = "aboutUsText", length = 250)
  private String aboutUsText;

}
