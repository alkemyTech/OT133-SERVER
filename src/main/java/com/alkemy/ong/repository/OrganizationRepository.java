package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.entity.Organization;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String>{
	
	@Query("SELECT p.name, p.image, p.phone, p.address, p.contact.facebookUrl, p.contact.linkedinUrl, p.contact.instagramUrl FROM Organization p")
	public Iterable<Organization> readAllDefined();
	
	public boolean existsByEmail(String email);
  
  @Query("SELECT o FROM Organization o ORDER BY o.id DESC")
  List<Organization> findTopById();

}
