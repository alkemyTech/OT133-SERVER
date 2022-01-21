package com.alkemy.ong.entity;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

@Entity
@Data
@Table(name = "news")
@SQLDelete(sql = "UPDATE table_product SET softDelete = true WHERE id=?")
@FilterDef(name = "deletedProductFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedProductFilter", condition = "softDelete = :isDeleted")
public class News {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "new_id")
    private String newId;
    
    @NotNull
    private String name;
    
    @NotNull
    private String content;
    
    @NotNull
    private String image;
    
//    @ManyToOne
//    @JoinColumn(name = "categoryId", referencedColumnName = "category_id")
//    @NotNull
//    private Category categoryId;
    
    private LocalDateTime timestamps;
    
    private boolean softDelete = Boolean.FALSE;
           
}
