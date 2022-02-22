package com.alkemy.ong.mapper;

import java.util.ArrayList;
import java.util.List;
import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.dto.OrganizationPublicDTO;
import com.alkemy.ong.dto.SlidePublicDTO;
import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.entity.contact.SocialLinks;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

  public OrganizationDTO toDTO(Organization organization) {

    OrganizationDTO dto = new OrganizationDTO();

    dto.setName(organization.getName());
    dto.setImage(organization.getImage());
    dto.setAddress(organization.getAddress());
    dto.setPhone(organization.getPhone());
    dto.setEmail(organization.getEmail());
    dto.setWelcomeText(organization.getWelcomeText());
    dto.setAboutUsText(organization.getAboutUsText());

    SocialLinks contact = organization.getContact();

    if (contact != null) {
      dto.setFacebookUrl(contact.getFacebookUrl());
      dto.setLinkedinUrl(contact.getLinkedinUrl());
      dto.setInstagramUrl(contact.getInstagramUrl());
      dto.setTimestamps(organization.getTimestamps());
    }


    return dto;
  }

  public Organization toEntity(OrganizationDTO dto) {
    Organization org = new Organization();
    org.setName(dto.getName());
    org.setImage(dto.getImage());
    org.setAddress(dto.getAddress());
    org.setPhone(dto.getPhone());
    org.setEmail(dto.getEmail());
    org.setWelcomeText(dto.getWelcomeText());
    org.setAboutUsText(dto.getAboutUsText());

    SocialLinks contact =
        new SocialLinks(dto.getFacebookUrl(), dto.getLinkedinUrl(), dto.getInstagramUrl());
    org.setContact(contact);

    return org;
  }


  public OrganizationPublicDTO organizationEntity2DTO(Organization org,
      List<SlidePublicDTO> slideDTOS) {
    OrganizationPublicDTO orgDTO = new OrganizationPublicDTO();

    orgDTO.setName(org.getName());
    orgDTO.setImage(org.getImage());
    orgDTO.setAddress(org.getAddress());
    orgDTO.setPhone(org.getPhone());

    SocialLinks contact = org.getContact();

    if (contact != null) {
      orgDTO.setFacebookUrl(contact.getFacebookUrl());
      orgDTO.setLinkedinUrl(contact.getLinkedinUrl());
      orgDTO.setInstagramUrl(contact.getInstagramUrl());
    }


    for (SlidePublicDTO slideDTO : slideDTOS) {

      orgDTO.addSlides(slideDTO);

    }

    return orgDTO;
  }
  
  public List<OrganizationDTO> OrganizationList2DTO(List<Organization> ongList){
	  List<OrganizationDTO> result = new ArrayList<>();
	  ongList.forEach(ong ->{
		  result.add(toDTO(ong));
	  });
	  return result;
  }
}
