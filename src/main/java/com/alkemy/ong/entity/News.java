package com.alkemy.ong.entity;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

@Entity
@Data
@Table(name = "news")
@SQLDelete(sql = "UPDATE table_product SET deleted = true WHERE id=?")
@FilterDef(name = "deletedProductFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedProductFilter", condition = "deleted = :isDeleted")
public class News {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "new_id")
    private Integer newId;
    
    @NotNull
    private String name;
    
    @NotNull
    private String content;
    
    @NotNull
    private String image;
    
    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "category_id")
    private Category category;
    
    private LocalDateTime date;
    
    private boolean deleted = Boolean.FALSE;
           
}
