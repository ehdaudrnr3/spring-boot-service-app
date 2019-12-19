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

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserLoginTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	private static final String CREATED_URI = "/api/user/create";
	private static final String LOGIN_URI = "/api/user/login";
	
	@Test
	public void validUser() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		UserDto userDto = new UserDto("test", "1234");
		String userJson = objectMapper.writeValueAsString(userDto);
		
		this.mockMvc.perform(post(CREATED_URI)
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print());
		
		ResultActions resultActions = this.mockMvc.perform(post(LOGIN_URI)
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print());
		
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.access_token").exists());
	}
	
	@Test
	public void userNotFound() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		UserDto userDto = new UserDto("test", "1234");
		String userJson = objectMapper.writeValueAsString(userDto);
		
		ResultActions resultActions = this.mockMvc.perform(post(LOGIN_URI)
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		
		resultActions
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void invalidPasswordUser() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		UserDto userDto = new UserDto("test", "1234");
		String userJson = objectMapper.writeValueAsString(userDto);
		
		UserDto loginUserDto = new UserDto("test", "4321");
		String loginUserJson = objectMapper.writeValueAsString(loginUserDto);
		
		this.mockMvc.perform(post(CREATED_URI)
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		
		ResultActions resultActions = this.mockMvc.perform(post(LOGIN_URI)
				.content(loginUserJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		
		resultActions
			.andExpect(status().isForbidden());
	}
	
	@Test
	public void emptyUserEmail() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		UserDto userDto = new UserDto("", "1234");
		String userJson = objectMapper.writeValueAsString(userDto);
		
		ResultActions resultActions = this.mockMvc.perform(post(LOGIN_URI)
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		
		resultActions
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void emptyUserPassword() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		UserDto userDto = new UserDto("test", "");
		String userJson = objectMapper.writeValueAsString(userDto);
		
		ResultActions resultActions = this.mockMvc.perform(post(LOGIN_URI)
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		
		resultActions
			.andExpect(status().isBadRequest());
	}
}

