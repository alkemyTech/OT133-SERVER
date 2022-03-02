package com.alkemy.ong.controller;


import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.entity.Category;
import com.alkemy.ong.entity.News;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.impl.NewServiceImpl;
import com.alkemy.ong.testEntity.NewMock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
class NewsControllerTest {
  private static final String ADMIN_CREDENTIALS = "admin@alkemy.org";
  private static final String PATH = "/news";

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  private NewsRepository newsRepository;

  @Autowired
  private CategoryRepository categoryRepository;
  // News guardado en la basade de datos provisoria,el id es uuid
  private News savedNews;

  private Category savedCategory;

  @Mock
  private NewServiceImpl newServiceImpl;

  @BeforeEach
  void setUp() {

    savedCategory = categoryRepository.save(NewMock.createCategoryMock());

    News newsSaved = new News();
    newsSaved.setId("12");
    newsSaved.setImage("12");
    newsSaved.setName("A valid name");
    newsSaved.setContent("A valid content");
    //newsSaved.setCategoryId();
    savedNews = newsRepository.save(newsSaved);

  }

  // Admin POST Activity -> Ok
  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void createNews_statusOK() throws Exception { 
//      Model de NewDTO    
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

    News createdEntity = newsRepository.findById(savedNews.getId()).get();

    assertEquals(createdEntity.getName(), "A valid name");
    then(createdEntity.getName()).isEqualTo("A valid name");
    then(createdEntity.getImage()).isEqualTo("12");

  }

  // Admin POST Activity -> Ok
  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void createNewImageEmpty() throws Exception {

    NewDTO request = NewMock.createNewMock();
    request.setCategoryId(savedCategory.getId());
    request.setImage("");

    ResultActions result = mockMvc
      .perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

    result.andExpect(status().isNotFound());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void createNewImageNull() throws Exception {

    NewDTO request = NewMock.createNewMock();
    request.setCategoryId(savedCategory.getId());
    request.setImage(null);

    ResultActions result = mockMvc
      .perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

    result.andExpect(status().isNotFound());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void createNewImageEmptySpace() throws Exception {

    NewDTO request = NewMock.createNewMock();
    request.setCategoryId(savedCategory.getId());
    request.setImage(" ");

    ResultActions result = mockMvc
      .perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

    result.andExpect(status().isNotFound());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void createNewContentNull() throws Exception {

    NewDTO request = NewMock.createNewMock();
    request.setCategoryId(savedCategory.getId());
    request.setContent(null);

    ResultActions result = mockMvc
      .perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

    result.andExpect(status().isNotFound());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void createNewContentEmpty() throws Exception {

    NewDTO request = NewMock.createNewMock();
    request.setCategoryId(savedCategory.getId());
    request.setContent("");

    ResultActions result = mockMvc
      .perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

    result.andExpect(status().isNotFound());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void createNewContentEmptySpace() throws Exception {

    NewDTO request = NewMock.createNewMock();
    request.setCategoryId(savedCategory.getId());
    request.setContent(" ");

    ResultActions result = mockMvc
      .perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

    result.andExpect(status().isNotFound());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void createNewNameEmptySpace() throws Exception {

    NewDTO request = NewMock.createNewMock();
    request.setCategoryId(savedCategory.getId());
    request.setName(" ");

    ResultActions result = mockMvc
      .perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

    result.andExpect(status().isNotFound());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void createNewNameEmpty() throws Exception {

    NewDTO request = NewMock.createNewMock();
    request.setCategoryId(savedCategory.getId());
    request.setName("");

    ResultActions result = mockMvc
      .perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

    result.andExpect(status().isNotFound());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void createNewNameNull() throws Exception {

    NewDTO request = NewMock.createNewMock();
    request.setCategoryId(savedCategory.getId());
    request.setName(null);

    ResultActions result = mockMvc
      .perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

    result.andExpect(status().isNotFound());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void createNewCategoryEmpty() throws Exception {

    NewDTO request = NewMock.createNewMock();
    request.setCategoryId("");

    ResultActions result = mockMvc
      .perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

    result.andExpect(status().isNotFound());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void createNewCategoryEmptySpace() throws Exception {

    NewDTO request = NewMock.createNewMock();
    request.setCategoryId(" ");

    ResultActions result = mockMvc
      .perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

    result.andExpect(status().isNotFound());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void createNewCategoryNoExist() throws Exception {

    NewDTO request = NewMock.createNewMock();
    request.setCategoryId("213");

    ResultActions result = mockMvc
      .perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

    result.andExpect(status().isNotFound());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void createNewCategoryNull() throws Exception {

    NewDTO request = NewMock.createNewMock();
    request.setCategoryId(null);

    ResultActions result = mockMvc
      .perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

    result.andExpect(status().isNotFound());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void updateActivity_statusOK() throws Exception {


    NewDTO request = NewMock.createNewMock();
    request.setCategoryId(savedCategory.getId());

    ResultActions result = mockMvc.perform(put(PATH + "/{id}", savedNews.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .content(toJson(request)));

    result.andExpect(status().isOk());

  }


  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void updateActivity_statusOK2() throws Exception {

    //para indagar que el id no se encuentree
    NewDTO request = NewMock.createNewMock();
    request.setCategoryId(savedCategory.getId());

    ResultActions result = mockMvc.perform(put(PATH + "/{id}", "a" + savedNews.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .content(toJson(request)));

    result.andExpect(status().isNotFound());

  }


  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void deleteIdCorrectOK() throws Exception {

    NewDTO request = NewMock.createNewMock();
    request.setCategoryId(savedCategory.getId());
    savedNews.setCategoryId(request.getCategoryId());


    ResultActions result = mockMvc.perform(delete(PATH + "/{id}", savedNews.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .content(toJson(request)));

    result.andExpect(status().isOk());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void deleteIdIncorrectNotFound() throws Exception {

    NewDTO request = NewMock.createNewMock();
    request.setCategoryId(savedCategory.getId());
    savedNews.setCategoryId(request.getCategoryId());


    ResultActions result = mockMvc.perform(delete(PATH + "/{id}", "1" + savedNews.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .content(toJson(request)));

    result.andExpect(status().isNotFound());

  }


  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void getByIdWithIdCorrect() throws Exception {

    NewDTO request = NewMock.createNewMock();
    request.setCategoryId(savedCategory.getId());
    savedNews.setCategoryId(request.getCategoryId());

    ResultActions result = mockMvc.perform(get(PATH + "/{id}", savedNews.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .content(toJson(request)));

    result.andExpect(status().isOk());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void getByIdWithIdIncorrect() throws Exception {

    NewDTO request = NewMock.createNewMock();
    request.setCategoryId(savedCategory.getId());
    savedNews.setCategoryId(request.getCategoryId());
    String idIncorect = "2680043c-f3a7-44b0-94f3-1cbf6254cd52";
    ResultActions result = mockMvc.perform(get(PATH + "/{id}", idIncorect)
      .contentType(MediaType.APPLICATION_JSON)
      .content(toJson(request)));

    result.andExpect(status().isNotFound());

  }


  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void pagitionIsOkWithCorrect() throws Exception {

    int pageOk = 2;
    ResultActions result = mockMvc.perform(get(PATH + "/page" + "/{page}", pageOk)
      .contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void pagitionIsOkWithZero() throws Exception {

    int pageZero = 0;
    ResultActions result = mockMvc.perform(get(PATH + "/page" + "/{page}", pageZero)
      .contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());

  }

  @Test
  @WithUserDetails(ADMIN_CREDENTIALS)
  void pagitionIsBadRequest() throws Exception {

    int pageIncorrect = -1;
    ResultActions result = mockMvc.perform(get(PATH + "/page" + "/{page}", pageIncorrect)
      .contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isBadRequest());

  }

  public static String toJson(Object value) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();
    return objectMapper.writeValueAsString(value);
  }


}