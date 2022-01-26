package com.alkemy.ong.security.payload;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {

    private String username;
    private String lastname;
    private String email;
    private Set<String> role;
    private String password;
}
