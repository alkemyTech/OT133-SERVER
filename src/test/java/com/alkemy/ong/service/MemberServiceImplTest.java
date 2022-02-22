package com.alkemy.ong.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.entity.member.Member;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.repository.MemberRepository;

@SpringBootTest
class MemberServiceImplTest {

	@Mock
	MemberRepository memberRepository;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	MemberMapper memberMapper;
	
	Member member;
	
	@BeforeEach
	void setUp() throws Exception {
		member = new Member();
		member.setDescription("descrption test");
		member.setName("name test");
		member.setImage("image test");
	}

	@Test
	void testSave() {
		when(this.memberRepository.save(any(Member.class))).thenReturn(this.member);
		assertNotNull(this.memberService.save(this.memberMapper.menberEntity2DTO(this.member)));
		
	}
	

	@Test
	void testFindById_IsNull() {
		when(this.memberRepository.findById("id-member")).thenReturn(null);
		assertNull(this.memberService.findById("id-member"));
	}
	
	
	@Test
	void testFindById_IsOk() {
		when(this.memberRepository.findById("edf8b161-78cb-4850-8a61-d842def69210")).thenReturn(Optional.of(this.member));
		MemberDTO expected = this.memberService.findById("edf8b161-78cb-4850-8a61-d842def69210");
		
		assertNotNull(expected);
	}
	
	@Test
	void testListAllMember() {
		List<Member> list = Arrays.asList(this.member,this.member,this.member);
		when(this.memberRepository.findAll()).thenReturn(list);
		List<MemberDTO> listDTO = this.memberService.listAllMember();
		
		assertThat(listDTO.isEmpty()).isFalse();
	}

	
	@Test
	void testGetPaginated_Page_Null() {
		List<Member> list = Arrays.asList(this.member,this.member,this.member,this.member,this.member,this.member);
		when(this.memberRepository.findAll()).thenReturn(list);
		List<MemberDTO> listDTO = this.memberService.getPaginated(null);
		
		assertThat(listDTO.isEmpty()).isFalse();
	}
	
	
	@Test
	void testGetPaginated_Page_Not_Null() {
		Pageable pageable = PageRequest.of(0, 6);
		when(this.memberRepository.findAll(pageable)).thenReturn(null);
		List<MemberDTO> listDTO = this.memberService.getPaginated(0);
		
		assertThat(listDTO.isEmpty()).isFalse();
	}

	@Test
	void testUpdate() {
		when(this.memberRepository.save(this.member)).thenReturn(this.member);
		MemberDTO memberDTO = this.memberService.update("edf8b161-78cb-4850-8a61-d842def69210", 
														this.memberMapper.menberEntity2DTO(this.member));
		
		assertNotNull(memberDTO);
	}
	
	
	@Test
	void testDelete() {
		doNothing().when(this.memberRepository).delete(this.member);
		this.memberService.delete("edf8b161-78cb-4850-8a61-d842def69210");
		
	}


}
