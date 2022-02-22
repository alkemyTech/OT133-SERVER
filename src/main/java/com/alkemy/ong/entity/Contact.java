package com.alkemy.ong.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contacts")
@SQLDelete(sql = "UPDATE contacts SET soft_delete = true WHERE id=?")
@FilterDef(name = "deletedContactFilter",
        parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedContactFilter", condition = "soft_delete = :isDeleted")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Contact extends PersistentEntity {

	private static final long serialVersionUID = 9188916451014161955L;

	@Column(name = "name", nullable = false)
    @NotNull
    private String name;

    @Column(name = "phone", nullable = false)
    private Long phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "message", nullable = false)
    private String message;
}
