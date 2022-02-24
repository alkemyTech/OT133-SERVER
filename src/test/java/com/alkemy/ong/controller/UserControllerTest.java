package com.alkemy.ong.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.*;

import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.Rol;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.enums.Roles;
import com.alkemy.ong.exception.UserException;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private ObjectMapper objectMapper;

    @Autowired
    private UserController userController;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private MockMvc mockMvc;

    private UserDTO dto;
    private User user1;
    private User user2;
    private Rol rolAdmin;
    private Rol rolUser;


    @BeforeEach
    public void setUp(){

        rolAdmin = new Rol(Roles.ROL_ADMIN, "ADMIN");
        rolUser = new Rol(Roles.ROL_USER, "USER");

        user1 = new User();
        user1.setId("1");
        user1.setEmail("user1@email");
        user1.setFirstName("User1");
        user1.setLastName("lastName");
        user1.setPassword("password");
        user1.setPhoto("photo");
        user1.getRoles().add(rolAdmin);
        userRepository.save(user1);

        user2 = new User();
        user2.setId("2");
        user2.setEmail("user2@email");
        user2.setFirstName("User2");
        user2.setLastName("lastName");
        user2.setPassword("password");
        user2.setPhoto("photo");
        user2.getRoles().add(rolUser);
        userRepository.save(user2);

        dto = new UserDTO();
        dto.setEmail(user2.getEmail());
        dto.setFirstName("nameDTO");
        dto.setLastName("lastNameDTO");

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
        .andExpect(MockMvcResultMatchers.status().isOk());
        
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);

        when(userServiceImpl.listAll()).thenReturn(Arrays.asList(user1, user2));
        assertTrue(userController.listAll().equals(ResponseEntity.ok(list)));
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

        String id = "ffc5ced3-2e57-4d56-ae32-add466b982eb";
        Map<String, Object> response = new HashMap<>();
        response.put("User eliminado", "Id: " + id);

        when(userServiceImpl.userExists(id)).thenReturn(true);
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user2));

        mockMvc.perform(MockMvcRequestBuilders.delete("/users" + "/{id}", id))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(response)));
    }

    @WithUserDetails(ROL_ADMIN)
    @Test
    public void deleteUserByAdminTest404() throws UserException, IOException{

        when(userServiceImpl.userExists(user1.getId())).thenReturn(false);
        assertTrue( ((userController.deleteUser(user1.getId())).getStatusCodeValue()) == HttpStatus.SC_NOT_FOUND );
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
        UUID id = UUID.fromString("ffc5ced3-2e57-4d56-ae32-add466b982eb");

        dto.setFirstName("name");
        dto.setLastName("Correa");
        dto.setEmail("murielcorrea@mail.com");
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
