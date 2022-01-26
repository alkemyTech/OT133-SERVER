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
    @Column(name = "slide_id")
    private String slideId;
    //Cambie el tipo de la variable por que generaba conflicto para ejecutar el proyecto.

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "text")
    private String text;

    @Column(name = "order_number")
    private Integer orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organizationId;
}