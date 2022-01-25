package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String>{
	
	@Query("SELECT p.name, p.image, p.phone, p.address FROM Organization p")
	public Iterable<Organization> readAllDefined();
}
