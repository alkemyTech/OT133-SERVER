package com.alkemy.ong.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "activities")
@SQLDelete(sql = "UPDATE activities SET soft_delete = true WHERE id=?")
@FilterDef(name = "deletedActivityFilter",
    parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedActivityFilter", condition = "soft_delete = :isDeleted")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Activity extends PersistentEntity {

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "content", nullable = false)
  private String content;

  @Column(name = "image", nullable = false)
  private String image;

}
