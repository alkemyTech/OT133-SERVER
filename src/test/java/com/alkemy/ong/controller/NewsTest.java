package com.alkemy.ong.controller;


import com.alkemy.ong.entity.Category;
import com.alkemy.ong.entity.News;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.service.NewService;
import com.alkemy.ong.service.impl.NewServiceImpl;
import com.alkemy.ong.testEntity.NewMock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityNotFoundException;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class NewsTest {

	private static final String PATH = "/news";

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	private NewsRepository newsRepository;
	@Autowired
	private NewController newController;
	@Autowired
	private CategoryRepository categoryRepository;
	// character guardado en la basade de datos provisoria,el id se incrementa
	private News savedNews;

	private Category savedCategory;

	//private NewServiceImpl testimonialService;

	@BeforeEach
	void setUp() {

		savedCategory = categoryRepository.save(NewMock.createCategoryMock());

		News newsSaved = new News();
		newsSaved.setId("12");
		newsSaved.setImage("12");
		newsSaved.setName("A valid name");
		newsSaved.setContent("A valid content");
		newsSaved.setCategoryId("123");
		savedNews = newsRepository.save(newsSaved);

	}

	// Admin POST Activity -> Ok
	@Test
	@WithUserDetails("admin@alkemy.org")
	void createNews_statusOK() throws Exception {

//    	NewDTO newDTO=new NewDTO();
//    	newDTO.setId("12");
//    	newDTO.setImage("12");
//    	newDTO.setName("A valid name");
//    	newDTO.setContent("A valid content");

		NewDTO request = NewMock.createNewMock();
		request.setCategoryId(savedCategory.getId());

		ResultActions result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

		result.andExpect(status().isCreated());

		String idDeEntidadGUardad = savedNews.getId();
		News createdEntity = newsRepository.findById(savedNews.getId()).get();

		assertEquals(createdEntity.getName(), "A valid name");
		then(createdEntity.getName()).isEqualTo("A valid name");
		then(createdEntity.getImage()).isEqualTo("12");

	}

	// Admin POST Activity -> Ok
	@Test
	@WithUserDetails("admin@alkemy.org")
	void createNewImageEmpty() throws Exception {

		NewDTO request = NewMock.createNewMock();
		request.setCategoryId(savedCategory.getId());
		request.setImage("");

		ResultActions result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

		result.andExpect(status().isNotFound());

	}

	@Test
	@WithUserDetails("admin@alkemy.org")
	void createNewImageNull() throws Exception {

		NewDTO request = NewMock.createNewMock();
		request.setCategoryId(savedCategory.getId());
		request.setImage(null);

		ResultActions result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

		result.andExpect(status().isNotFound());

	}

	@Test
	@WithUserDetails("admin@alkemy.org")
	void createNewImageEmptySpace() throws Exception {

		NewDTO request = NewMock.createNewMock();
		request.setCategoryId(savedCategory.getId());
		request.setImage(" ");

		ResultActions result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

		result.andExpect(status().isNotFound());

	}

	@Test
	@WithUserDetails("admin@alkemy.org")
	void createNewContentNull() throws Exception {

		NewDTO request = NewMock.createNewMock();
		request.setCategoryId(savedCategory.getId());
		request.setContent(null);

		ResultActions result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

		result.andExpect(status().isNotFound());

	}

	@Test
	@WithUserDetails("admin@alkemy.org")
	void createNewContentEmpty() throws Exception {

		NewDTO request = NewMock.createNewMock();
		request.setCategoryId(savedCategory.getId());
		request.setContent("");

		ResultActions result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

		result.andExpect(status().isNotFound());

	}

	@Test
	@WithUserDetails("admin@alkemy.org")
	void createNewContentEmptySpace() throws Exception {

		NewDTO request = NewMock.createNewMock();
		request.setCategoryId(savedCategory.getId());
		request.setContent(" ");

		ResultActions result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

		result.andExpect(status().isNotFound());

	}

	@Test
	@WithUserDetails("admin@alkemy.org")
	void createNewNameEmptySpace() throws Exception {

		NewDTO request = NewMock.createNewMock();
		request.setCategoryId(savedCategory.getId());
		request.setName(" ");

		ResultActions result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

		result.andExpect(status().isNotFound());

	}

	@Test
	@WithUserDetails("admin@alkemy.org")
	void createNewNameEmpty() throws Exception {

		NewDTO request = NewMock.createNewMock();
		request.setCategoryId(savedCategory.getId());
		request.setName("");

		ResultActions result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

		result.andExpect(status().isNotFound());

	}

	@Test
	@WithUserDetails("admin@alkemy.org")
	void createNewNameNull() throws Exception {

		NewDTO request = NewMock.createNewMock();
		request.setCategoryId(savedCategory.getId());
		request.setName(null);

		ResultActions result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

		result.andExpect(status().isNotFound());

	}

	@Test
	@WithUserDetails("admin@alkemy.org")
	void createNewCategoryEmpty() throws Exception {

		NewDTO request = NewMock.createNewMock();
		request.setCategoryId("");

		ResultActions result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

		result.andExpect(status().isNotFound());

	}

	@Test
	@WithUserDetails("admin@alkemy.org")
	void createNewCategoryEmptySpace() throws Exception {

		NewDTO request = NewMock.createNewMock();
		request.setCategoryId(" ");

		ResultActions result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

		result.andExpect(status().isNotFound());

	}

	@Test
	@WithUserDetails("admin@alkemy.org")
	void createNewCategoryNoExist() throws Exception {

		NewDTO request = NewMock.createNewMock();
		request.setCategoryId("213");

		ResultActions result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

		result.andExpect(status().isNotFound());

	}

	@Test
	@WithUserDetails("admin@alkemy.org")
	void createNewCategoryNull() throws Exception {

		NewDTO request = NewMock.createNewMock();
		request.setCategoryId(null);

		ResultActions result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

		result.andExpect(status().isNotFound());

	}

//    //GET BY ID //
//    @Test
//    @WithUserDetails("admin@alkemy.org")
//    void getByIDTest() throws Exception {
//    	System.out.println("asdrasdada: "+savedNews.getId());
//    	System.out.println("safafafaf: "+savedNews.toString());
//    	//Mockito.when(newController.getById(savedNews.getId())).thenReturn(new ResponseEntity(HttpStatus.OK));
//    	
//    	//savedCategory=categoryRepository.save(NewMock.createCategoryMock());
//    	
//    	String givenId = savedNews.getId();
////
//    	Mockito.doThrow(Exception.class).when(testimonialService.getById(givenId));
////		mockMvc.perform(get(String.format("%s/%s", PATH, givenId)))
////				.andExpect(MockMvcResultMatchers.status().isNotFound());
////    
////    	
////    	ResultActions result = mockMvc.perform(get("/news/{id}", "03d231cf-97e8-4bf4-8d7c-feb389827e20"));
////
////        result.andExpect(status().isOk());
////        result.andExpect(jsonPath("$.name").value(savedNews.getName()));
////        result.andExpect(jsonPath("$.content").value(savedNews.getContent()));
////        result.andExpect(jsonPath("$.image").value(savedNews.getImage()));
////        result.andExpect(jsonPath("$.category.id").value(savedNews.getCategory().getId()));
//
//	     //Given
//
//        //When
//        //var result = mockMvc.perform(get(String.format("%s/%s", PATH, savedNews.getId())));
//
//        //Then:
//        //result.andExpect(status().isNotFound());
////        result.andExpect(jsonPath("$.name").value(savedNews.getName()));
////        result.andExpect(jsonPath("$.content").value(savedNews.getContent()));
////        result.andExpect(jsonPath("$.image").value(savedNews.getImage()));
////        result.andExpect(jsonPath("$.category.id").value(savedNews.getCategory().getId()));
////		
//		
//		
//    }
//    



	@Test
    @WithUserDetails("admin@alkemy.org")
    void updateActivity_statusOK() throws Exception {

    	
        NewDTO request = NewMock.createNewMock();
        request.setCategoryId(savedCategory.getId());
        
        ResultActions result = mockMvc.perform(put(PATH + "/{id}", savedNews.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        result.andExpect(status().isOk());

  }
	
	
	@Test
    @WithUserDetails("admin@alkemy.org")
    void updateActivity_statusOK2() throws Exception {

    	
        NewDTO request = NewMock.createNewMock();
        request.setCategoryId(savedCategory.getId());
        
        ResultActions result = mockMvc.perform(put(PATH + "/{id}", "a"+savedNews.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        result.andExpect(status().isNotFound());

  }
	
	
//	@Test
//    @WithUserDetails("admin@alkemy.org")
//    void updateActivity_statusOK23() throws Exception {
//
//		String givenId = "03d231cf-97e8-4bf4-8d7c-feb389827e20";
//	    mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", PATH, givenId)))
//	        .andExpect(MockMvcResultMatchers.status().isOk());
//
//  }
	
	
	
	
	

	public static String toJson(Object value) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		return objectMapper.writeValueAsString(value);
	}

	public static <T> T fromJson(String value, Class<T> valueType) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		return objectMapper.readValue(value, valueType);
	}

}