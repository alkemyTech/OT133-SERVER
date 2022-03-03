package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.entity.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.impl.CategoryServiceImpl;
import com.alkemy.ong.testEntity.CategoryMock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {
  private static final String ADMIN_CREDENTIALS = "admin@alkemy.org";
  private static final String USER_CREDENTIALS = "user@mail.com";
  private static final String PATH = "/categories";

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  private CategoryRepository categoryRepository;

  private Category savedCategory;

  @Mock
  private CategoryServiceImpl categoryServiceImpl;

  @BeforeEach
  void setUp() {


    Category categorySaved = new Category();
    categorySaved.setId("12");
    categorySaved.setImage("12");
    categorySaved.setName("A valid name");
    categorySaved.setDescription("A valid content");
    //categorySaved.setCategoryId();
    savedCategory = categoryRepository.save(categorySaved);

  }

  // Admin POST Activity -> Ok
  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void createCategory_statusOK() throws Exception { 
//      Model de CategoryDTO    
//    	CategoryDTO categoryDTO=new CategoryDTO();
//    	categoryDTO.setId("12");
//    	categoryDTO.setImage("12");
//    	categoryDTO.setName("A valid name");
//    	categoryDTO.setDescription("A valid content");

    CategoryDTO request = CategoryMock.createCategoryDTOMock();
    //request.setCategoryId(savedCategory.getId());

    ResultActions result = mockMvc
      .perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

    result.andExpect(status().isCreated());

    Category createdEntity = categoryRepository.findById(savedCategory.getId()).get();

    assertEquals(createdEntity.getName(), "A valid name");
    then(createdEntity.getName()).isEqualTo("A valid name");
    then(createdEntity.getImage()).isEqualTo("12");

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void createCategoryNameEmpty() throws Exception {

    CategoryDTO request = CategoryMock.createCategoryDTOMock();

    request.setName("");

    ResultActions result = mockMvc
      .perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

    result.andExpect(status().isNotAcceptable());

  }


  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void updateCategory_statusOK() throws Exception {


    CategoryDTO request = CategoryMock.createCategoryDTOMock();
   

    ResultActions result = mockMvc.perform(put(PATH + "/{id}", savedCategory.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .content(toJson(request)));

    result.andExpect(status().isOk());

  }


  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void updateCategory_statusOK2() throws Exception {

    //para indagar que el id no se encuentree
    CategoryDTO request = CategoryMock.createCategoryDTOMock();
    String idIncorect = "2680043c-f3a7-44b0-94f3-1cbf6254cd52";

    ResultActions result = mockMvc.perform(put(PATH + "/{id}", idIncorect)
      .contentType(MediaType.APPLICATION_JSON)
      .content(toJson(request)));

    result.andExpect(status().isNotFound());

  }


  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void deleteIdCorrectOK() throws Exception {

    CategoryDTO request = CategoryMock.createCategoryDTOMock();


    ResultActions result = mockMvc.perform(delete(PATH + "/{id}", savedCategory.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .content(toJson(request)));

    result.andExpect(status().isOk());

  }

  

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void getByIdWithIdCorrect() throws Exception {

    CategoryDTO request = CategoryMock.createCategoryDTOMock();
 

    ResultActions result = mockMvc.perform(get(PATH + "/{id}", savedCategory.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .content(toJson(request)));

    result.andExpect(status().isOk());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void getByIdWithIdIncorrect() throws Exception {

    CategoryDTO request = CategoryMock.createCategoryDTOMock();
    Mockito.when(categoryServiceImpl.findById("2680043c-f3a7-44b0-94f3-1cbf6254cd52")).thenReturn(null);
  
    String idIncorect = "";
    ResultActions result = mockMvc.perform(get(PATH + "/{id}", idIncorect)
      .contentType(MediaType.APPLICATION_JSON)
      .content(toJson(request)));

    result.andExpect(status().isForbidden());

  }


  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void paginationIsOkWithCorrect() throws Exception {

    int pageOk = 2;
    ResultActions result = mockMvc.perform(get(PATH)
      .contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());

  }

  @Test
  @WithUserDetails(USER_CREDENTIALS)
  void paginationIsOkWithZero() throws Exception {

    int pageZero = 0;
    ResultActions result = mockMvc.perform(get(PATH)
      .contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());

  }

  public static String toJson(Object value) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();
    return objectMapper.writeValueAsString(value);
  }


}
