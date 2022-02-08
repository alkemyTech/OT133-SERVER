package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.entity.member.Member;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

  // --------------------------------------------------------------------------------------------
  // Autowireds
  // --------------------------------------------------------------------------------------------

  @Autowired
  MemberMapper memberMapper;

  @Autowired
  MemberRepository memberRepository;


  // --------------------------------------------------------------------------------------------
  // Create
  // --------------------------------------------------------------------------------------------
  
  @Override
  public MemberDTO save(MemberDTO memberDTO){
      Member member = memberMapper.memberDTO2Entity(memberDTO);
      memberRepository.save(member);
      MemberDTO memberResponse = memberMapper.menberEntity2DTO(member);
      return memberResponse;
  }
  // --------------------------------------------------------------------------------------------
  // Read
  // --------------------------------------------------------------------------------------------

  public MemberDTO findById(String id){
    Optional<Member> member = memberRepository.findById(id);
    if(member.isPresent()) {
      MemberDTO request = memberMapper.menberEntity2DTO(member.get());
      return request;
    }
    return null;
  }

   public List<MemberDTO> listAllMember() {
    List<Member>memberList=memberRepository.findAll();
    List<MemberDTO>memberDTOS=memberMapper.MemberlistMember2listDTO(memberList);
    return memberDTOS;
    }
  // --------------------------------------------------------------------------------------------
  // Update
  // --------------------------------------------------------------------------------------------

  public MemberDTO update (String id, MemberDTO memberDTO) {
    Member member = memberRepository.findById(id).get();
    Member memberUpdate = memberMapper.memberDTO2Entity(memberDTO, member);
    memberRepository.save(memberUpdate);
    MemberDTO request = memberMapper.menberEntity2DTO(memberUpdate);
    return request;
  }
  

  // --------------------------------------------------------------------------------------------
  // Delete
  // --------------------------------------------------------------------------------------------

  public void delete(String id) {
    Member member = memberRepository.findById(id).get();
    memberRepository.delete(member);
  }
}
