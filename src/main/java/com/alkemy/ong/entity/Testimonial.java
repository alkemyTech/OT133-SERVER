package com.alkemy.ong.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import java.time.LocalDate;



@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "testimonials")
@SQLDelete(sql = "UPDATE testimonials SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Testimonial {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "testimonial_id", nullable = false)
    private String testimonialID;

    @Column(name = "name", nullable = false)
    private String name;

    private String image;

    private String content;

    private boolean deleted = Boolean.FALSE;


    @DateTimeFormat(
            pattern = "yyyy/MM/dd"
    )
    private LocalDate date = LocalDate.now();




}
