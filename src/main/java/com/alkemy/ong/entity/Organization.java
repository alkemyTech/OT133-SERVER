package com.alkemy.ong.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "organizations")
@SQLDelete(sql = "UPDATE organization SET softDelete=true WHERE id = ?")
/**
 * FilterDef: define los requerimientos, los cuales son usados por Filter
 * @author Mauro*/
@FilterDef(
		name = "softDeleteFilter",
		parameters = @ParamDef(name = "isDeleted", type = "boolean")
		)
/**
 * Usa las definiciones de FilterDef, usando el nombre del parámetro definido.
 * name: el nombre que se definio en FilterDef
 * condition: condición para aplicar el filtro en función del parámetro
 * La idea de añadir el Filter y FilterDef es que en nuestro controller se añada
 * la notación Filter al método findAll(), para filtrar.
 * @author Mauro*/
@Filter(
		name = "softDeleteFilter",
		condition = "softDelete = :isDeleted"
		)
public class Organization implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id_organization", nullable = false, unique = true)
	private String idOrganization;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "image", nullable = false)
	private String image;
	
	@Column(name = "address", nullable = true, length = 100)
	private String address;
	
	@Column(name = "phone", nullable = true)
	private String phone;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "welcomeText", nullable = false, length = 250)
	private String welcomeText;
	
	@Column(name = "aboutUsText", nullable = true, length = 250)
	private String aboutUsText;
	
	@Column(name = "timestamps")
	@JsonFormat(pattern = "dd-MMM-yyy", locale = "es")
	private Date timestamps;
	
	private boolean softDelete;
	
	
	public Organization(String name, String image, String address, String phone, String email, String welcomeText,
			String aboutUsText, Date timestamps, boolean softDelete) {
		super();
		this.name = name;
		this.image = image;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.welcomeText = welcomeText;
		this.aboutUsText = aboutUsText;
		this.timestamps = timestamps;
		this.softDelete = softDelete;
	}

	public Organization() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getIdOrganization() {
		return idOrganization;
	}

	public void setIdOrganization(String idOrganization) {
		this.idOrganization = idOrganization;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWelcomeText() {
		return welcomeText;
	}

	public void setWelcomeText(String welcomeText) {
		this.welcomeText = welcomeText;
	}

	public String getAboutUsText() {
		return aboutUsText;
	}

	public void setAboutUsText(String aboutUsText) {
		this.aboutUsText = aboutUsText;
	}

	public Date getTimestamps() {
		return timestamps;
	}

	public void setTimestamps(Date timestamps) {
		this.timestamps = timestamps;
	}

	public boolean isSoftDelete() {
		return softDelete;
	}

	public void setSoftDelete(boolean softDelete) {
		this.softDelete = softDelete;
	}
	
}
