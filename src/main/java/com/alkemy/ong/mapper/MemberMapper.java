package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.entity.member.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

  public Member toEntity(MemberDTO dto) {
    return new Member(dto.getName(), dto.getFacebookUrl(), dto.getInstagramUrl(), dto.getLinkedinUrl(),
      dto.getImage(), dto.getDescription());
  }

  public MemberDTO toDTO(Member member) {
    return new MemberDTO(member.getId(), member.getName(), member.getFacebookUrl(), member.getInstagramUrl(),
      member.getLinkedinUrl(), member.getImage(), member.getDescription());
  }

}
