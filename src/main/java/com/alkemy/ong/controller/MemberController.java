package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.exception.MemberException;
import com.alkemy.ong.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import java.io.IOException;
import java.util.*;

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
  
  @PostMapping
  @PreAuthorize("hasAuthority('ROL_USER')")
  public ResponseEntity<Object> createMember(@Validated @RequestBody MemberDTO memberDTO) throws MemberException, IOException{
    try{
      MemberDTO member = memberService.save(memberDTO); 
      return ResponseEntity.status(HttpStatus.CREATED).body(member);
    } catch ( Exception e){
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Exception: " + e.getLocalizedMessage());
    }
  }
  
  
  @GetMapping("/page/{page}")
  public ResponseEntity<?> getPaginated(@PathVariable Integer page){
	  Map<String, Object> response = new HashMap<>();
	  if(page > 0) {
		 response.put("url previus", String.format("localhost:8080/members/page/%d", page - 1 ));
	  }
	  
	  if(!this.memberService.getPaginated(page + 1).isEmpty()) {
			 response.put("url next", String.format("localhost:8080/members/page/%d", page + 1 ));
	  }
	  
	  response.put("ok", this.memberService.getPaginated(page));
	  return ResponseEntity.ok(response);
  }
  

  @ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MemberException.class)
	public Map<String, String> handleMemberExceptions() {
    Map<String, String> error = new HashMap<>();
    error.put( "Ok: " + Boolean.FALSE , "Cannot process the request, verify if the petiton is correct");
    return error;
  	}
}
