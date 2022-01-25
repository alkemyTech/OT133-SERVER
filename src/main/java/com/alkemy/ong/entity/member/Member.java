package com.alkemy.ong.entity.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.alkemy.ong.entity.PersistentEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "members")
@SQLDelete(sql = "UPDATE members SET softDelete = true WHERE id=?")
@Where(clause = "softDelete=false")
// @FilterDef(name = "deletedMemberFilter",
// parameters = @ParamDef(name = "isDeleted", type = "boolean"))
// @Filter(name = "deletedMemberFilter", condition = "softDelete = :isDeleted")
@Getter
@Setter
@NoArgsConstructor
public class Member extends PersistentEntity {

    @NotNull(message = "El nombre no puede ser nulo")
    @Column(nullable = false)
    private String name;
    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;
    @NotNull(message = "La imagen no puede ser nula")
    @Column(nullable = false)
    private String image;
    private String description;
}
