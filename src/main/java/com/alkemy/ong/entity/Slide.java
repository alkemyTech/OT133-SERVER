package com.alkemy.ong.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "slides")
public class Slide {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id_Slide")
    private Integer idSlide;

    @Column(name = "id_Slide")
    private String imageUrl;

    @Column(name = "text")
    private String text;

    @Column(name = "order")
    private Integer order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Organization")
    private Organization idOrganization;
}