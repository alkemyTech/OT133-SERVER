package com.alkemy.ong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends PersistentEntity{

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User user;

  @Column(name = "user_id")
  private String userId;
  
  private String body;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "news_id", insertable = false, updatable = false)
  private News news;

  @Column(name = "news_id")
  private String newsId;
  
}
