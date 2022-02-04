package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.entity.member.Member;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
  @Autowired
  MemberRepository memberRepository;
  @Autowired
  MemberMapper memberMapper;
  @Override
  public List<MemberDTO> listAllMember() {
    List<Member>memberList=memberRepository.findAll();
    List<MemberDTO>memberDTOS=memberMapper.MemberlistMember2listDTO(memberList);
    return memberDTOS;
  }
}
