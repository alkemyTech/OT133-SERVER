package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/members")
public class MemberController {
  
  @Autowired
  MemberService memberService;
 
  @GetMapping
  @PreAuthorize("hasAuthority('ROL_ADMIN')")
  public ResponseEntity<List<MemberDTO>> listAll(){
    List<MemberDTO> memberList = memberService.listAllMember();
    return ResponseEntity.status(HttpStatus.OK).body(memberList);
  }
  
  
}
