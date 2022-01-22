package com.alkemy.ong.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "activities")
@Getter
@Setter
@SQLDelete(sql = "UPDATE activities SET delete = true WHERE id=?")
@Where(clause = "deleted=false")
public class Activity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "activity_id", nullable = false)
    private String id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "content", nullable = false)
    private String content; 
    
    @Column(name = "image", nullable = false)
    private String image; 
    
    @Column(name = "timestamps", nullable = false)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate timestamps;
    
    @Column(name = "softDelete",nullable = false)
    private boolean softDelete = Boolean.FALSE;
    
}
