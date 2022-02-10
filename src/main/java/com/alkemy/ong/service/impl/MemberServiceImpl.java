package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.entity.member.Member;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Objects;

@Service
public class MemberServiceImpl implements MemberService {

	private static final int PAGE_SIZE = 5;
	
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
   
	@Override
	public List<MemberDTO> getPaginated(Integer page) {
		if (Objects.isNull(page)) {
			return this.memberRepository.findAll().stream().map(this.memberMapper::menberEntity2DTO)
					.collect(Collectors.toList());
		}
		Pageable pageable = PageRequest.of(page, PAGE_SIZE);
		return this.memberRepository.findAll(pageable).getContent().stream().map(this.memberMapper::menberEntity2DTO)
				.collect(Collectors.toList());
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
