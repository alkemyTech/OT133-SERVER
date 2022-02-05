package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

  @Autowired
  MemberService memberService;

  @PreAuthorize("hasAuthority('ROL_ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    if (memberService.findById(id) == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    memberService.delete(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PreAuthorize("hasAuthority('ROL_USER')")
  @PutMapping("/{id}")
  public ResponseEntity<MemberDTO> update(@PathVariable String id, @Valid @RequestBody MemberDTO dto) {
    if (memberService.findById(id) == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(memberService.update(id, dto));
  }
  
  @GetMapping
  @PreAuthorize("hasAuthority('ROL_ADMIN')")
  public ResponseEntity<List<MemberDTO>> listAll(){
    List<MemberDTO> memberList = memberService.listAllMember();
    return ResponseEntity.status(HttpStatus.OK).body(memberList);
  }
  
}
