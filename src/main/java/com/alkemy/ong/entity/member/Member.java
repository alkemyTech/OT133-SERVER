package com.alkemy.ong.entity.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.alkemy.ong.entity.PersistentEntity;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "members")
@SQLDelete(sql = "UPDATE members SET soft_delete = true WHERE id=?")
@FilterDef(name = "deletedMemberFilter",
        parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedMemberFilter", condition = "soft_delete = :isDeleted")
@Getter
@Setter
@NoArgsConstructor
public class Member extends PersistentEntity {

    @Column(name = "name", nullable = false)
    @NotNull(message = "El nombre no puede ser nulo")
    private String name;
    @Column(name = "facebook_url")
    private String facebookUrl;
    @Column(name = "instagram_url")
    private String instagramUrl;
    @Column(name = "linkedin_url")
    private String linkedinUrl;
    @Column(name = "image", nullable = false)
    @NotNull(message = "La imagen no puede ser nula")
    private String image;
    @Column(name = "description")
    private String description;

}
