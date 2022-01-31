package com.alkemy.ong.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.OrganizationService;

@Service
public class OrganizationServiceImp implements OrganizationService{
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	@Override
	public Iterable<Organization> readAllDefined() {
		return organizationRepository.readAllDefined();
	}

	@Override
	public Organization save(Organization organization) {
		return organizationRepository.save(organization);
	}

	@Override
	public boolean existsByEmail(String email) {
		return organizationRepository.existsByEmail(email);
	}

}
