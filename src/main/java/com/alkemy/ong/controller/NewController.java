package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("news")
public class NewController {

    @Autowired
    NewService newService;

    @PostMapping
    @PreAuthorize("ROL_ADMIN")
    public ResponseEntity<NewDTO> save(NewDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(newService.save(dto));
    }
}
