package com.alkemy.ong.controller;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.exception.MemberException;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.impl.MemberServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

	private static final String USER_CREDENTIALS = "user@mail.com";

	private static final String ADMIN_CREDENTIALS = "invidato@mail.com";

	private static final String route = "/members";

	private String baseUrl;

	private MemberDTO memberDTO;

	private ObjectMapper objectMapper;

	@MockBean
	private MemberRepository memberRepository;

	@MockBean
	private MemberServiceImpl memberServiceImpl;

	@Autowired
	MemberController memberController;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		this.memberDTO = new MemberDTO();
		this.memberDTO.setName("member name test");
		this.memberDTO.setImage("image member test");

		this.baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
		this.objectMapper = new ObjectMapper();
	}

	@Test
	void whenGet_andNotLoggedIn_thenUnauthorized() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(route)).andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenGets_andAdminLoggedIn_thenOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(route)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithUserDetails(USER_CREDENTIALS)
	void whenGets_andUserLoggedIn_thenForbidden() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(route)).andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenGet_aValidPage_thenOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(route.concat("/page/0")))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenGet_aValidPage_and_notZero_then_containsPrevious_and_isOk() throws Exception {

		Integer page = 1;

		mockMvc.perform(MockMvcRequestBuilders.get(String.format("%s/page/%d", route, page)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string(
						Matchers.containsString(String.format("\"url previus\":\"%s/page/%d\"", baseUrl, page - 1))));

	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenGet_aValidPage_and_hasNext_then_containsNext_and_isOk() throws Exception {
		Integer page = 1;

		List<MemberDTO> notAnEmptyList = Arrays.asList(this.memberDTO, this.memberDTO, this.memberDTO);
		Mockito.when(this.memberServiceImpl.getPaginated(page + 1)).thenReturn(notAnEmptyList);

		mockMvc.perform(MockMvcRequestBuilders.get(String.format("%s/page/%d", route, page)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string(
						Matchers.containsString(String.format("\"url next\":\"%s/page/%d\"", baseUrl, page + 1))));
	}

///////////////////////////////////////////////////////////////////////
// POST
///////////////////////////////////////////////////////////////////////

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPost_withNameNull_then_isBadRequest() throws Exception {

		this.memberDTO.setName(null);

		mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(this.memberDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPost_withNameEmpty_then_isBadRequest() throws Exception {

		this.memberDTO.setName("");

		mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(this.memberDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPost_withNameBlank_then_isBadRequest() throws Exception {

		this.memberDTO.setName("                                                ");

		mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(this.memberDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPost_withImageNull_then_isBadRequest() throws Exception {

		this.memberDTO.setImage(null);

		mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(this.memberDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPost_withImageEmpty_then_isBadRequest() throws Exception {

		this.memberDTO.setImage("");

		mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(this.memberDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPost_withImageBlank_then_isBadRequest() throws Exception {

		this.memberDTO.setImage("                 ");

		mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(this.memberDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void whenPost_notLoggedIn_thenUnauthorized() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(this.memberDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithUserDetails(USER_CREDENTIALS)
	void whenPost_noAdmin_thenForbidden() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(this.memberDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPost_Admin_thenConflictInternal() throws Exception {

		Mockito.when(this.memberServiceImpl.save(this.memberDTO)).thenThrow(ConstraintViolationException.class);
		mockMvc.perform(MockMvcRequestBuilders.post(route).content(this.getJSON(this.memberDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isConflict());
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPost_Admin_then_throw_MemberException() throws Exception {

		Mockito.doThrow(MemberException.class).when(this.memberServiceImpl).save(this.memberDTO);
		mockMvc.perform(MockMvcRequestBuilders.post(route).content(this.getJSON(this.memberDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	@Transactional
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPost_aValidDTO_then_isCreated() throws Exception {
		Mockito.when(this.memberServiceImpl.save(this.memberDTO)).thenReturn(this.memberDTO);

		mockMvc.perform(MockMvcRequestBuilders.post(route).content(getJSON(this.memberDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string(getJSON(this.memberDTO)));
	}

///////////////////////////////////////////////////////////////////////
//PUT
///////////////////////////////////////////////////////////////////////

	@Test
	void whenPut_notLoggedIn_then_isUnauthorized() throws Exception {
		String givenId = "a-test-ID";

		mockMvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, givenId))
				.content(getJSON(this.memberDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithUserDetails(USER_CREDENTIALS)
	void whenPut_noAdmin_then_isForbidden() throws Exception {
		String givenId = "a-test-ID";

		mockMvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, givenId))
				.content(getJSON(this.memberDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPut_and_doesNotExists_then_isNotFound() throws Exception {

		String givenId = "a-test-ID";
		Mockito.when(this.memberServiceImpl.findById(givenId)).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, givenId))
				.content(getJSON(this.memberDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPut_and_doesNotExists_then_EntityNotFoundException() throws Exception {

		String givenId = "a-test-ID";
		Mockito.when(this.memberServiceImpl.findById(givenId)).thenReturn(this.memberDTO);
		Mockito.when(this.memberServiceImpl.update(givenId, this.memberDTO)).thenThrow(EntityNotFoundException.class);

		mockMvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, givenId))
				.content(getJSON(this.memberDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@Transactional
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPut_aValidDTO_then_isOK() throws Exception {

		String id = "edf8b161-78cb-4850-8a61-d842def69210";
		Mockito.when(this.memberServiceImpl.findById(id)).thenReturn(this.memberDTO);
		Mockito.when(this.memberServiceImpl.update(id, this.memberDTO)).thenReturn(this.memberDTO);

		mockMvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, id)).content(getJSON(this.memberDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string(getJSON(this.memberDTO)));
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPut_withNameNull_then_isBadRequest() throws Exception {

		this.memberDTO.setName(null);

		mockMvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, "ID-ABC"))
				.content(getJSON(this.memberDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPut_withNameBlank_then_isBadRequest() throws Exception {

		this.memberDTO.setName("                       ");

		mockMvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, "ID-ABC"))
				.content(getJSON(this.memberDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPut_withNameEmpty_then_isBadRequest() throws Exception {

		this.memberDTO.setName("");

		mockMvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, "ID-ABC"))
				.content(getJSON(this.memberDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPut_withImageNull_then_isBadRequest() throws Exception {

		this.memberDTO.setImage(null);

		mockMvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, "ID-ABC"))
				.content(getJSON(this.memberDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPut_withImageEmpty_then_isBadRequest() throws Exception {

		this.memberDTO.setImage("");

		mockMvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, "ID-ABC"))
				.content(getJSON(this.memberDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenPut_withImageBlank_then_isBadRequest() throws Exception {

		this.memberDTO.setImage("           ");

		mockMvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s", route, "ID-ABC"))
				.content(getJSON(this.memberDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	///////////////////////////////////////////////////////////////////////
	// DELETE
	///////////////////////////////////////////////////////////////////////

	@Test
	void whenDelete_notLoggedIn_then_isUnauthorized() throws Exception {
		String givenId = "a-test-ID";

		mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", route, givenId)))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithUserDetails(USER_CREDENTIALS)
	void whenDelete_notAdmin_then_isForbidden() throws Exception {
		String givenId = "a-test-ID";

		mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", route, givenId)))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@Transactional
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenDelete_and_doesNotExist_then_isNotFound() throws Exception {
		String id = "a-test-ID";

		Mockito.doThrow(EntityNotFoundException.class).when(this.memberServiceImpl).delete(id);
		mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", route, id)))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@Transactional
	@WithUserDetails(ADMIN_CREDENTIALS)
	void whenDelete_and_exists_then_isOk() throws Exception {
		String id = "edf8b161-78cb-4850-8a61-d842def69210";
		Mockito.when(this.memberServiceImpl.findById("edf8b161-78cb-4850-8a61-d842def69210"))
				.thenReturn(this.memberDTO);

		this.mockMvc.perform(MockMvcRequestBuilders.delete(route + "/{id}", id))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	// --------------------------------------------------------------------------------------------
	// Internal Methods
	// --------------------------------------------------------------------------------------------

	private String getJSON(MemberDTO dto) throws JsonProcessingException {
		return objectMapper.writeValueAsString(dto);
	}

}
