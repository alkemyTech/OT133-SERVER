package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
  public ResponseEntity<MemberDTO> update(@PathVariable String id) {
    if (memberService.findById(id) == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(memberService.update(id));
  }
}
