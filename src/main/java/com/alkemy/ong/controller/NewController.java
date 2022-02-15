package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.entity.News;
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
    @ApiResponse(responseCode = "401",
      description = "OPSss")
    ,
    @ApiResponse(responseCode = "403",
      description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_403_DESCRIPTION)
  })
  public ResponseEntity<NewDTO> save(@Valid NewDTO dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(newService.save(dto));
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
  @PreAuthorize("hasRole('ROL_ADMIN')")
  @Operation(summary = DocumentationMessages.NEWS_CONTROLLER_SUMMARY_UPDATE,description = DocumentationMessages.NEWS_CONTROLLER_SUMMARY_UPDATE_DESCRIPTION)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "404",
      description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_404_DESCRIPTION)
  })
  public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody NewDTO newDto){
    System.out.println(id);
    if(!newService.existsById(id)) {
      return new ResponseEntity<>("No existe", HttpStatus.NOT_FOUND);
    }
    System.out.println(id);
    System.out.println(newDto.toString());

    News news = newService.getById(id);
    news.setName(newDto.getName());
    news.setImage(newDto.getImage());
    news.setContent(newDto.getContent());

    NewDTO newDTO = newMapper.entity2newDTO(news);
    newService.save(newDTO);

    return new ResponseEntity(HttpStatus.OK);
  }
  
  
  //delete
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROL_ADMIN')")
  @Operation(summary = DocumentationMessages.NEWS_CONTROLLER_SUMMARY_DELETE,description = DocumentationMessages.NEWS_CONTROLLER_SUMMARY_DELETE_DESCRIPTION)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200",
      description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_200_DESCRIPTION)
    ,
    @ApiResponse(responseCode = "204",
      description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_204_DESCRIPTION)
    ,
    @ApiResponse(responseCode = "404",
      description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_404_DESCRIPTION)
  })
  public ResponseEntity<?> delete(@PathVariable("id") String id){
    if(!newService.existsById(id)) {
      return new ResponseEntity("No existe", HttpStatus.NOT_FOUND);
    }
    newService.delete(id);
    return new ResponseEntity(HttpStatus.OK);
  }



}
