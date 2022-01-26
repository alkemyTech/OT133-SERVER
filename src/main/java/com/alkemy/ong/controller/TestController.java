package com.alkemy.ong.controller;


import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/test")
public class TestController {

    @GetMapping("/all")
    public String freeAccess(){
        return "Publico";
    }

    @GetMapping("/user")
    //@Secured("ROL_USER")
    @PreAuthorize("hasRole('ROL_USER')")
    public String userAccess(){
        return "User";
    }

    @GetMapping("/admin")
    //@Secured("ROL_ADMON")
    @PreAuthorize("hasRole('ROL_ADMIN')")
    public String adminAccess(){
        return "Admin";
    }
}
