package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.entity.Category;
import com.alkemy.ong.entity.member.Member;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.mapper.NewMapper;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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


  // --------------------------------------------------------------------------------------------
  // Read
  // --------------------------------------------------------------------------------------------

  public MemberDTO findById(String id){
    Optional<Member> member = memberRepository.findById(id);
    if(member.isPresent()) {
      MemberDTO request = memberMapper.toDTO(member.get());
      return request;
    }
    return null;
  }

  // --------------------------------------------------------------------------------------------
  // Update
  // --------------------------------------------------------------------------------------------

  public MemberDTO update (String id) {
    MemberDTO dto = this.findById(id);
    Member member = memberMapper.toEntity(dto);
    memberRepository.save(member);
    MemberDTO request = memberMapper.toDTO(member);
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
