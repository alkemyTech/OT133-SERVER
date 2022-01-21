package com.alkemy.ong.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET softDelete = true WHERE id = ?")
@Where(clause = "deleted = false")
public class User {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid",strategy = "uuid2")
	private String id;
	
	@Column(name = "first_name",nullable = false)
	private String firstName;
	
	@Column(name = "last_name",nullable = false)
	private String lastName;
	
	@Column(nullable = false,unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = true)
	private String photo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private Integer roleId;
	
	@Column(name="timestamps")
	private LocalDateTime timeStamps;
	
	@Column(name="softDelete")
	private Boolean softDelete = Boolean.FALSE;
}
