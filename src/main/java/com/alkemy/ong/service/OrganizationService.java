package com.alkemy.ong.service;

import com.alkemy.ong.dto.OrganizationPublicDTO;
import com.alkemy.ong.entity.Organization;

import java.util.List;

public interface OrganizationService {

  List<OrganizationPublicDTO> readAllDefined();
	
	Organization save(Organization organization);
	
	boolean existsByEmail(String email);

}
