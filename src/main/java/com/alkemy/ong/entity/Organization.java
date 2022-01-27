package com.alkemy.ong.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "organizations")
@SQLDelete(sql = "UPDATE organizations SET soft_delete = true WHERE organizationId=?")
@Where(clause = "soft_delete=false")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Organization extends PersistentEntity {

	// ? No entiendo para qué está esta constante - por las dudas la dejo
	private static final long serialVersionUID = 1L;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "image", nullable = false)
	private String image;

	@Column(name = "address", length = 100)
	private String address;

	@Column(name = "phone")
	private Integer phone;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "welcomeText", nullable = false, length = 250)
	private String welcomeText;

	@Column(name = "aboutUsText", length = 250)
	private String aboutUsText;

}
