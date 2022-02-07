package com.alkemy.ong.mapper;


import com.alkemy.ong.dto.OrganizationPublicDTO;
import com.alkemy.ong.dto.SlidePublicDTO;
import com.alkemy.ong.entity.Organization;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrganizationMapper {

  public OrganizationPublicDTO organizationEntity2DTO(Organization member, List<SlidePublicDTO> slideDTOS) {
    OrganizationPublicDTO memberDTO = new OrganizationPublicDTO();

    memberDTO.setName(member.getName());
    memberDTO.setImage(member.getImage());
    memberDTO.setAddress(member.getAddress());
    memberDTO.setPhone(member.getPhone());
    memberDTO.setFacebookUrl(member.getFacebookUrl());
    memberDTO.setInstagramUrl(member.getInstagramUrl());
    memberDTO.setLinkedinUrl(member.getLinkedinUrl());
    for (SlidePublicDTO slideDTO:slideDTOS) {
      memberDTO.addSlides(slideDTO);
    }
    
    return memberDTO;
  }

}
