package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.entity.News;
import com.alkemy.ong.mapper.NewMapper;
import com.alkemy.ong.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("news")
public class NewController {

  @Autowired
  NewService newService;
  
  @Autowired
  NewMapper newMapper;

  @PostMapping
  @PreAuthorize("hasRole('ROL_ADMIN')")
  public ResponseEntity<NewDTO> save(NewDTO dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(newService.save(dto));
  }
  
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROL_ADMIN')")
  public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody NewDTO newDto){
	  
	  if(!newService.existsById(id)) {
		  return new ResponseEntity<>("No existe", HttpStatus.NOT_FOUND);
	  }
	  
	  News news = newService.getById(id);
	  news.setName(newDto.getName());
	  news.setImage(newDto.getImage());
	  news.setContent(newDto.getContent());
	  
	  NewDTO newDTO = newMapper.entity2newDTO(news);
	  newService.save(newDTO);
	  
	  return new ResponseEntity(HttpStatus.OK);
  }
  
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROL_ADMIN')")
  public ResponseEntity<?> delete(@PathVariable("id") String id){
	  if(!newService.existsById(id)) {
		  return new ResponseEntity("No existe", HttpStatus.NOT_FOUND);
	  }
	  newService.delete(id);
	  return new ResponseEntity(HttpStatus.OK);
  }
}
