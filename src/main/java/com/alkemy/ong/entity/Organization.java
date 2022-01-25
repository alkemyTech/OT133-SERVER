package com.alkemy.ong.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Entity
@Table(name = "organizations")
@SQLDelete(sql = "UPDATE organizations SET deleted = true WHERE organizationId=?")
/***
 * this annotation is filter to be softdelete
 * @author mdo
 *
 */
@Where(clause = "deleted=false")
public class Organization implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "organizationId", nullable = false, unique = true)
	private String organizationId;
	
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamps")
	private Date timestamps;
	
	private boolean deleted = Boolean.FALSE;
	
	/***
	 * this method create date in a moment of the object is created
	 * @author mdo
	 */
	@PrePersist
	private void onCreate() {
		timestamps = new Date();
	}
	
	
	public Organization(String name, String image, String address, Integer phone, String email, String welcomeText,
			String aboutUsText) {
		super();
		this.name = name;
		this.image = image;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.welcomeText = welcomeText;
		this.aboutUsText = aboutUsText;
	}

	public Organization() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getOrganizationId() {
		return organizationId;
	}


	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
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


	public Integer getPhone() {
		return phone;
	}


	public void setPhone(Integer phone) {
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


	public boolean isDeleted() {
		return deleted;
	}


	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
}
