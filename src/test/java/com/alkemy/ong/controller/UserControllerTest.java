package com.alkemy.ong.controller;

import java.io.IOException;
import java.util.*;

import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.exception.UserException;
import com.alkemy.ong.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private static final String ROL_USER = "user@mail.com";
    private static final String ROL_ADMIN = "admin@alkemy.org";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private ObjectMapper objectMapper;
    
    private UserDTO dto;

    @BeforeEach
    public void setUp(){

        dto = new UserDTO();
        
        this.objectMapper = new ObjectMapper();
    }

    // Get

    @Test
    void getIfNotLoggedInUnauthorized() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    } 

    @WithUserDetails(ROL_ADMIN)
    @Test
    void listAllByAdminTest() throws Exception{
        
        mockMvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        
    } 

    @WithUserDetails(ROL_USER)
    @Test
    public void listAllByUserTest() throws Exception{
        
        mockMvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    // Delete

    @Test
    void deleteIfNotLoggedInUnauthorized() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/users").contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @WithUserDetails(ROL_ADMIN)
    @Transactional
    @Test
    public void deleteUserByAdminTest() throws UserException, IOException, Exception{

        User user = userRepository.findByEmail("alkemy@mail.com").get();
        String id = user.getId();
        Map<String, Object> response = new HashMap<>();
        response.put("User eliminado", "Id: " + id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/users" + "/{id}", id))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(response)));
    }

    @WithUserDetails(ROL_ADMIN)
    @Test
    public void deleteUserByAdminTest404() throws UserException, IOException, Exception{

        String id = "ffc5ced3-2e57-4d56-ae32-add466b982ec";
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/users"+ "/{id}", id).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
        
    }

    @WithUserDetails(ROL_USER)
    @Test
    public void deleteUserByUserTest() throws Exception{

        String id = "ffc5ced3-2e57-4d56-ae32-add466b982eb";
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/users"+ "/{id}", id).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    // Put

    @Test
    void putIfNotLoggedInUnauthorized() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/users").contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @WithUserDetails(ROL_ADMIN)
    @Test
    public void updateUserTest() throws Exception {

        Map<Object, Object> fields = new HashMap<>();
        fields.put("firstName", "name");
        User user = userRepository.findByEmail("alkemy@mail.com").get();
        UUID id = UUID.fromString(user.getId());

        dto.setFirstName("name");
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        Map<String, Object> response = new HashMap<>();
        response.put("ok", dto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/users" + "/{id}", id)
        .content(getJSON(fields)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(response)));

    }

    @WithUserDetails(ROL_USER)
    @Test
    public void updateUserTest404() throws Exception {

        Map<Object, Object> fields = new HashMap<>();
        fields.put("firstName", "name");
        UUID id = UUID.fromString("ffc5ced3-2e57-4d56-ae32-add466b982eb");

        Map<String, Object> response = new HashMap<>();
        response.put("Error", String.format("User with ID %s not found.", id));

        mockMvc.perform(MockMvcRequestBuilders.patch("/users" + "/{id}", id)
        .content(getJSON(fields)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(response)));
    }

    @WithUserDetails(ROL_ADMIN)
    @Test
    public void updateUserTest400() throws Exception{

        UUID id = UUID.fromString("ffc5ced3-2e57-4d56-ae32-add466b982eb");

        mockMvc.perform(MockMvcRequestBuilders.patch("/users" + "/{id}", id).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private String getJSON(Map<Object, Object> fields) throws JsonProcessingException {
        return objectMapper.writeValueAsString(fields);
    } 
}
