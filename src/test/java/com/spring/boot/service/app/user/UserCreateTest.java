package com.spring.boot.service.app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.service.app.models.UserDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserCreateTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	private static final String CREATED_URI = "/api/user/create";
	
	@Test
	public void validCreateUser() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		UserDto user = new UserDto("test", "password");
		String userJson = objectMapper.writeValueAsString(user);
		
		ResultActions resultActions = mockMvc.perform(post(CREATED_URI)
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.email").value(user.getEmail()));
	}
	
	@Test
	public void emptyEmail() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		UserDto user = new UserDto("", "");
		String userJson = objectMapper.writeValueAsString(user);
		
		ResultActions resultActions = mockMvc.perform(post(CREATED_URI)
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print());
		
		resultActions.
			andExpect(status().isBadRequest());
	}
	
	@Test
	public void emptyPassword() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		UserDto user = new UserDto("test", "");
		String userJson = objectMapper.writeValueAsString(user);
		
		ResultActions resultActions = mockMvc.perform(post(CREATED_URI)
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print());
		
		resultActions
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void emptyUser() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		UserDto user = new UserDto("", "");
		String userJson = objectMapper.writeValueAsString(user);
		
		ResultActions resultActions = mockMvc.perform(post(CREATED_URI)
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print());
		
		resultActions
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void emptyContent() throws Exception {
		ResultActions resultActions = mockMvc.perform(post(CREATED_URI)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print());
		
		resultActions
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void confiltUser() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		UserDto user = new UserDto("test", "password");
		String userJson = objectMapper.writeValueAsString(user);
		
		mockMvc.perform(post(CREATED_URI)
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		
		ResultActions resultActions = mockMvc.perform(post(CREATED_URI)
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		
		resultActions
			.andExpect(status().isConflict());
	}
}
