package com.alkemy.ong.entity;

import javax.persistence.*;

import com.sun.istack.NotNull;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "news")
@SQLDelete(sql = "UPDATE table_product SET soft_delete = true WHERE id=?")
@FilterDef(name = "deletedProductFilter",
    parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedProductFilter", condition = "soft_delete = :isDeleted")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class News extends PersistentEntity {

  @Column(name = "name", nullable = false)
  @NotNull
  private String name;

  @Column(name = "content", nullable = false)
  @NotNull
  private String content;

  @Column(name = "image", nullable = false)
  @NotNull
  private String image;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "category_id", insertable = false, updatable = false)
  private Category category;

  @Column(name = "category_id")
  private String categoryId;

}
