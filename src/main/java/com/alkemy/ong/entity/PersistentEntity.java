package com.alkemy.ong.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
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
@EntityListeners(AuditingEntityListener.class)
public abstract class PersistentEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @CreatedDate
    @Column(name = "timestamps", columnDefinition = "TIMESTAMP")
    private LocalDateTime timestamps;

    @Column(name = "soft_delete")
    private boolean softDelete = Boolean.FALSE;
}
