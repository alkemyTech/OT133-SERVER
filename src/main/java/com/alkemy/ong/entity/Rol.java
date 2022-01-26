package com.alkemy.ong.entity;

import com.alkemy.ong.enums.ERole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "roles")
@Setter
@Getter
public class Rol {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id_rol")
    private String idRol;

    @Column(name = "name")
    @NotNull
    @Enumerated(EnumType.STRING)
    private ERole name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "timestamps")
    private LocalDateTime timestamps;

}