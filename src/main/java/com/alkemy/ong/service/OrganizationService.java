package com.alkemy.ong.service;

import com.alkemy.ong.entity.Organization;

public interface OrganizationService {
	
	Iterable<Organization> readAllDefined();
	
	Organization save(Organization organization);
	
	boolean existsByEmail(String email);

}
