package com.alkemy.ong.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

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
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name ="user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "id_rol")
	)
	private Set<Rol> roles = new HashSet<>();
	
	@Column(name="timestamps")
	private LocalDateTime timeStamps;
	
	@Column(name="softDelete")
	private Boolean softDelete = Boolean.FALSE;

	public User(String username, String lastname, String email, String encode) {
	}
}
