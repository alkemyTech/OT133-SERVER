package com.alkemy.ong.entity;

import com.alkemy.ong.entity.News;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "categories")
@SQLDelete(sql = "UPDATE categories SET deleted = true WHERE category_id=?")
@Where(clause = "deleted=false")
@FilterDef(name = "deletedCategoryFilter", parameters = @ParamDef(name = "deleted", type = "boolean"))
@Filter(name = "deletedCategoryFilter", condition = "deleted = :isDeleted")
public class Category {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "category_id")
    private String categoryId; 

    private String name;
    private String description;
    private String image;
    private boolean deleted = false;
    private LocalDateTime date = LocalDateTime.now();

    @OneToMany(mappedBy = "categories", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<News> news = new ArrayList<>();

    public void agregarNew(News new1){  
        this.news.add(new1);
        new1.setCategory(this);
    } 

    public String getId(){
        return categoryId;
    }
     
    public String getName(){
        return name;
    }

    public void setName(String name1){
        this.name = name1;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String d){
        this.description = d;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String im){
        this.image = im;
    }

    public List<News> getNews(){
        return news;
    }

    public void setNews(List<News> newsList){
        this.news = newsList;
    } 

    

}
