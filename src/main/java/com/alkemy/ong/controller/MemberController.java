package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.exception.MemberException;
import com.alkemy.ong.messages.DocumentationMessages;
import com.alkemy.ong.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
  @Operation(summary = DocumentationMessages.MEMBER_CONTROLLER_SUMMARY_DELETE)
  @ApiResponses(value = {
		  @ApiResponse(responseCode = "204", description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_204_DESCRIPTION),
		  @ApiResponse(responseCode = "403", description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_403_DESCRIPTION),
		  @ApiResponse(responseCode = "404", description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_404_DESCRIPTION)
  })
  public ResponseEntity<Void> delete(@Parameter(description = "Id of the member to delete.") @PathVariable String id) {
    if (memberService.findById(id) == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    memberService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PreAuthorize("hasAuthority('ROL_ADMIN')")
  @PutMapping("/{id}")
  @Operation(summary = DocumentationMessages.MEMBER_CONTROLLER_SUMMARY_UPDATE)
  @ApiResponses(value = {
		  @ApiResponse(responseCode = "200", description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_200_DESCRIPTION),
		  @ApiResponse(responseCode = "400", description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_400_DESCRIPTION),
		  @ApiResponse(responseCode = "403", description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_403_DESCRIPTION),
		  @ApiResponse(responseCode = "404", description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_404_DESCRIPTION)
  })
  public ResponseEntity<MemberDTO> update(@Parameter(description = "Id of the member to update.") 
  											@PathVariable String id, 
  											@Valid @RequestBody MemberDTO dto) {
	  
    if (memberService.findById(id) == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(memberService.update(id, dto));
  }
  
  @GetMapping
  @PreAuthorize("hasAuthority('ROL_ADMIN')")
  @Operation(summary = DocumentationMessages.MEMBER_CONTROLLER_SUMMARY_LIST)
  @ApiResponses(value = {
		  @ApiResponse(responseCode = "200", description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_200_DESCRIPTION),
		  @ApiResponse(responseCode = "403", description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_403_DESCRIPTION),
  })
  public ResponseEntity<List<MemberDTO>> listAll(){
    List<MemberDTO> memberList = memberService.listAllMember();
    return ResponseEntity.status(HttpStatus.OK).body(memberList);
  }
  
  @PostMapping
  @PreAuthorize("hasAuthority('ROL_ADMIN')")
  @Operation(summary = DocumentationMessages.MEMBER_CONTROLLER_SUMMARY_CREATE)
  @ApiResponses(value = {
		  @ApiResponse(responseCode = "201", description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_201_DESCRIPTION),
		  @ApiResponse(responseCode = "400", description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_400_DESCRIPTION),
		  @ApiResponse(responseCode = "409", description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_409_DESCRIPTION),
  })
  public ResponseEntity<Object> createMember(@Validated @RequestBody MemberDTO memberDTO) throws MemberException, IOException{
	  try{
	      MemberDTO member = memberService.save(memberDTO); 
	      return ResponseEntity.status(HttpStatus.CREATED).body(member);
	    } catch (MemberException ex){
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exception: " + this.handleMemberExceptions());
	    }catch (Exception e){
	      return ResponseEntity.status(HttpStatus.CONFLICT).body("Exception: " + e.getLocalizedMessage());
	    }
  }
  
  
  @GetMapping("/page/{page}")
  @Operation(summary = DocumentationMessages.MEMBER_CONTROLLER_SUMMARY_LIST)
  @ApiResponses(value = {
		  @ApiResponse(responseCode = "200", description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_200_DESCRIPTION)
  })
  public ResponseEntity<?> getPaginated(@Parameter(description = "Page number to get members.", example = "0") 
  										@PathVariable Integer page){
	  
	  Map<String, Object> response = new HashMap<>();
	  String currentContextPath = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
	  if(page > 0) {
		 response.put("url previus", currentContextPath.concat(String.format("/members/page/%d", page - 1)));
	  }
	  
	  if(!this.memberService.getPaginated(page + 1).isEmpty()) {
		  response.put("url next", currentContextPath.concat(String.format("/members/page/%d", page + 1)));
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
