package com.alkemy.ong.entity.member;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.alkemy.ong.entity.PersistentEntity;
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
@Table(name = "members")
@SQLDelete(sql = "UPDATE members SET soft_delete = true WHERE id=?")
@FilterDef(name = "deletedMemberFilter",
    parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedMemberFilter", condition = "soft_delete = :isDeleted")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Member extends PersistentEntity {

  @Column(name = "name", nullable = false)
  @NotNull(message = "El nombre no puede ser nulo")
  private String name;

  @Embedded
  SocialLinks socialLinks;

  @Column(name = "image", nullable = false)
  @NotNull(message = "La imagen no puede ser nula")
  private String image;

  @Column(name = "description")
  private String description;

}
