package com.alkemy.ong.controller;

import java.sql.Array;
import java.util.Arrays;
import java.util.Optional;

import com.alkemy.ong.dto.CommentBodyDTO;
import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.exception.CommentException;
import com.alkemy.ong.service.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    private static final String ROL_USER = "user@mail.com";
    private static final String ROL_ADMIN = "admin@alkemy.org";
	
	@Autowired
	CommentController commentController;
	
	@MockBean
	private CommentService commentService;

	@Autowired
	private MockMvc mockMvc;
	
    private ObjectMapper objectMapper;

    private CommentBodyDTO bodyDTO1;
    private CommentBodyDTO bodyDTO2;
    private CommentDTO dto1;
    private CommentDTO dto2;

    @BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		this.objectMapper = new ObjectMapper();

        bodyDTO1 = new CommentBodyDTO("body");
        bodyDTO2 = new CommentBodyDTO("body");
        dto1 = new CommentDTO();
        dto1.setNewId("abc123");
        dto1.setUserId("abc123");
        dto1.setBody("body");
        dto2 = new CommentDTO();
        dto2.setNewId("abc123");
        dto2.setUserId("abc123");
        dto2.setBody("body");
	}

    // Get

	@Test
	void getNotLoggedIn() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/comments"))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithUserDetails(ROL_USER)
	void getByUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/comments"))
		.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithUserDetails(ROL_ADMIN)
	public void getOk() throws Exception {
		Mockito.when(commentService.bringCommentsBodies()).thenReturn(Arrays.asList(bodyDTO1, bodyDTO2));
		mockMvc.perform(MockMvcRequestBuilders.get("/comments"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	////////////////////////////////////////////

	@Test
	void findAllNotLoggedIn() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/comments/posts" + "/{id}" + "/comments", "abc123"))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithUserDetails(ROL_USER)
	void findAllByUser() throws Exception {
        Mockito.when(commentService.getAllComments("abc123")).thenReturn(Arrays.asList(dto1, dto2));
		mockMvc.perform(MockMvcRequestBuilders.get("/comments/posts" + "/{id}" + "/comments", "abc123"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithUserDetails(ROL_ADMIN)
	void findAllByAdmin() throws Exception {
        Mockito.when(commentService.getAllComments("abc123")).thenReturn(Arrays.asList(dto1, dto2));
		mockMvc.perform(MockMvcRequestBuilders.get("/comments/posts" + "/{id}" + "/comments", "abc123"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	// Post

	@Test
	void postNotLoggedIn() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/comments"))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	// Put

	@Test
	void putNotLoggedIn() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/comments"))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithUserDetails(ROL_ADMIN)
	void putCommentException() throws Exception {
        Mockito.when(commentService.validateUser("abc123")).thenReturn(404);
		mockMvc.perform(MockMvcRequestBuilders.put("/comments" + "/{id}", "abc123")
        .content(getJSON(dto1)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@WithUserDetails(ROL_ADMIN)
	void putByAdmin() throws Exception {
        Mockito.when(commentService.validateUser("abc123")).thenReturn(200);
		mockMvc.perform(MockMvcRequestBuilders.put("/comments" + "/{id}", "abc123")
        .content(getJSON(dto1)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	// Delete

	@Test
	void deleteNotLoggedIn() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/comments" + "/{id}", "abc"))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithUserDetails(ROL_USER)
	void deleteByUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/comments" + "/{id}", "abc"))
		.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

    @Test
	@WithUserDetails(ROL_ADMIN)
	void deleteException() throws Exception {
        Mockito.when(commentService.findById("abc")).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.delete("/comments" + "/{id}", "abc"))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	@WithUserDetails(ROL_ADMIN)
	void deleteOk() throws Exception {
        Mockito.when(commentService.findById("abc")).thenReturn(dto1);
		mockMvc.perform(MockMvcRequestBuilders.delete("/comments" + "/{id}", "abc"))
		.andExpect(MockMvcResultMatchers.status().isOk());

	}

	////////////////

	private String getJSON(CommentDTO commentDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(commentDTO);
    }
    
}
