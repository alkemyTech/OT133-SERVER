package com.alkemy.ong.service;

import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.dto.OrganizationPublicDTO;
import com.alkemy.ong.entity.Organization;

import java.util.List;
import java.util.Optional;

public interface OrganizationService {

	List<OrganizationPublicDTO> readAllDefinedPublic();
	
	List<OrganizationDTO> readAllDefined();
	
	Optional<Organization> findByEmail(String email);
	
	Organization save(Organization organization);
	
	boolean existsByEmail(String email);

}
