package com.alkemy.ong.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;
import lombok.Getter;
import lombok.Setter;

/**
 * Una implementación genérica a modo de conveniencia para la persistencia de entidades.
 * 
 * @author Tomás Sánchez
 * @version 1.0
 */
@MappedSuperclass
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public abstract class PersistentEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    // ? Encontré esta solucion para MySQL, pero creo que podría implementarse de otro modo
    @Column(insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime timestamps;
    private boolean softDelete = Boolean.FALSE;
}
