package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.entity.member.Member;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class MemberMapper {

  public MemberDTO menberEntity2DTO(Member member) {
    
    MemberDTO memberDTO = new MemberDTO();

    memberDTO.setName(member.getName());
    memberDTO.setImage(member.getImage());
    memberDTO.setDescription(member.getDescription());
    memberDTO.setInstagramUrl(member.getInstagramUrl());
    memberDTO.setFacebookUrl(member.getFacebookUrl());
    memberDTO.setLinkedinUrl(member.getLinkedinUrl());
  
    return memberDTO;
  }

  public Member memberDTO2Entity(MemberDTO memberDTO) {
    
    Member member = new Member();

    member.setName(memberDTO.getName());
    member.setImage(memberDTO.getImage());
    member.setDescription(memberDTO.getDescription());
    member.setInstagramUrl(memberDTO.getInstagramUrl());
    member.setFacebookUrl(memberDTO.getFacebookUrl());
    member.setLinkedinUrl(memberDTO.getLinkedinUrl());
  
    return member;
  }

  public List<MemberDTO> MemberlistMember2listDTO(List<Member> memberList) {
    
    List<MemberDTO> memberDTOS = new ArrayList<>();
    for (Member member : memberList) {
      memberDTOS.add(menberEntity2DTO(member));
    }
    
    return memberDTOS;
  }

  public Member memberDTO2Entity(MemberDTO memberDTO, Member member) {

    member.setName(memberDTO.getName());
    member.setImage(memberDTO.getImage());
    member.setDescription(memberDTO.getDescription());
    member.setInstagramUrl(memberDTO.getInstagramUrl());
    member.setFacebookUrl(memberDTO.getFacebookUrl());
    member.setLinkedinUrl(memberDTO.getLinkedinUrl());

    return member;
  }
  
}
