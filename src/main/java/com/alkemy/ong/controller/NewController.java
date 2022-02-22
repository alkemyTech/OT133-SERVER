package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.entity.News;
import com.alkemy.ong.exception.NewException;
import com.alkemy.ong.mapper.NewMapper;
import com.alkemy.ong.messages.DocumentationMessages;
import com.alkemy.ong.service.NewService;

import java.util.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("news")
public class NewController {

  @Autowired
  NewService newService;

  @Autowired
  NewMapper newMapper;
  //save
  @PostMapping
  @PreAuthorize("hasAuthority('ROL_ADMIN')")
  @Operation(summary = DocumentationMessages.NEWS_CONTROLLER_SUMMARY_CREATE,description = DocumentationMessages.NEWS_CONTROLLER_SUMMARY_CREATE_DESCRIPTION)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201",
      description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_201_DESCRIPTION)
    ,
    @ApiResponse(responseCode = "404",
      description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_404_DESCRIPTION)
  })
  public ResponseEntity<?> save(@RequestBody NewDTO dto) {
    
    try {
      
      NewDTO newDTOS = newService.save(dto);
      return ResponseEntity.status(HttpStatus.CREATED).body(newDTOS);

    } catch (NewException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } 
  }
  //detalle por id
  @PreAuthorize("hasAuthority('ROL_ADMIN')")
  @GetMapping("/{id}")
  @Operation(summary = DocumentationMessages.NEWS_CONTROLLER_SUMMARY_FINDBYID,description = DocumentationMessages.NEWS_CONTROLLER_SUMMARY_FINDBYID_DESCRIPTION)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200",
      description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_200_DESCRIPTION)
    ,
    @ApiResponse(responseCode = "404",
      description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_404_DESCRIPTION)
  })
  public ResponseEntity<?> getById(@PathVariable UUID id) {
    Map<String, Object> response = new HashMap<>();

    if (!this.newService.existsById(id.toString())) {
      response.put("NotFound", String.format("New with ID %s not found", id.toString()));
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    } else {
      response.put("ok", this.newMapper.entity2newDTO(this.newService.getById(id.toString())));
      return ResponseEntity.ok(response);
    }
  }
  //paginacion
  @GetMapping("/page/{page}")
  @Operation(summary = DocumentationMessages.NEWS_CONTROLLER_SUMMARY_PAGINATION,description = DocumentationMessages.NEWS_CONTROLLER_SUMMARY_PAGINATION_DESCRIPTION)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200",
      description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_200_DESCRIPTION)
    ,
    @ApiResponse(responseCode = "400",
      description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_400_DESCRIPTION)
  })
  public ResponseEntity<Map<String, Object>> getAllByPage(@PathVariable int page) {
    try {
      Map<String, Object> response = new HashMap<>();
      if(page > 0) {
        response.put("url previus", String.format("localhost:8080/news/page/%d", page - 1 ));
      }
      if(!newService.getAllByPage(page + 1).isEmpty()) {
        response.put("url next", String.format("localhost:8080/news/page/%d", page + 1 ));
      }
      response.put("ok", newService.getAllByPage(page));
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e){
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }
  
  
  // actualizacion
  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('ROL_ADMIN')")
  @Operation(summary = DocumentationMessages.NEWS_CONTROLLER_SUMMARY_UPDATE,description = DocumentationMessages.NEWS_CONTROLLER_SUMMARY_UPDATE_DESCRIPTION)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200",
      description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_200_DESCRIPTION)
    ,
    @ApiResponse(responseCode = "404",
      description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_404_DESCRIPTION)
  })
  public ResponseEntity<?> update(@PathVariable("id") String id,@RequestBody NewDTO newDto) {

    try {
      NewDTO newDTOS = newService.update(newDto, id);
      return ResponseEntity.status(HttpStatus.OK).body(newDTOS);
    } catch (NewException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }
  
  //delete
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ROL_ADMIN')")
  @Operation(summary = DocumentationMessages.NEWS_CONTROLLER_SUMMARY_DELETE,description = DocumentationMessages.NEWS_CONTROLLER_SUMMARY_DELETE_DESCRIPTION)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200",
      description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_200_DESCRIPTION)
    ,
    @ApiResponse(responseCode = "404",
      description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_404_DESCRIPTION)
  })
  public ResponseEntity<?> delete(@PathVariable("id") String id){
    if(!newService.existsById(id)) {
      return new ResponseEntity("The New id does not exist in the database or is incorrect.", HttpStatus.NOT_FOUND);
    }
    newService.delete(id);
    return new ResponseEntity(HttpStatus.OK);
  }

}
