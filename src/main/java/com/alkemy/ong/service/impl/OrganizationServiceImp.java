package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.OrganizationPublicDTO;
import com.alkemy.ong.dto.SlidePublicDTO;
import com.alkemy.ong.entity.Slide;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.repository.SlideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.OrganizationService;

import java.util.ArrayList;
import java.util.*;


@Service
public class OrganizationServiceImp implements OrganizationService {

  @Autowired
  OrganizationRepository organizationRepository;
  @Autowired
  SlideRepository slideRepository;
  @Autowired
  SlideMapper slideMapper;
  @Autowired
  OrganizationMapper organizationMapper;

  @Override
  public List<OrganizationPublicDTO> readAllDefined() {
    List<OrganizationPublicDTO> listOrganizationPublicDTOS = new ArrayList<>();
    try {
      //ordenando por id de forma descendente  5 4 ...
      List<Organization> organizationList = organizationRepository.findTopById();
      
      for (Organization organization : organizationList) {
        
        //buscamos en los slides asociados a cada organizacion
        List<Slide> slideList = slideRepository.findAllByOrganization(organization);
        //pasar de slite a dto
        List<SlidePublicDTO> slideDTOS = slideMapper.sliteList2Dto(slideList);
        
        //Ordenamos por orden ascendente 1 2 3 ...
        slideDTOS.sort(SlidePublicDTO.compareByOrder);
        
        //pasar organizacion a DTO
        OrganizationPublicDTO organizationPublicDTOS = organizationMapper.organizationEntity2DTO(organization, slideDTOS);
        
        //Guardamos en la lista a devolver
        listOrganizationPublicDTOS.add(organizationPublicDTOS);
        
      }
    } catch (Exception e) {
      System.out.println("Error Service: " + e);
    }
    
    return listOrganizationPublicDTOS;
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
