package com.spring.boot.service.app.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.Map;

import org.junit.Before;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.service.app.constant.Constants;
import com.spring.boot.service.app.models.UserDto;
import com.spring.boot.service.app.models.response.RestReponseModel;
import com.spring.boot.service.app.service.TokenService;
import com.spring.boot.service.app.utils.DateUtil;

import io.jsonwebtoken.Claims;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRefreshTokenTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TokenService tokenService;
	
	private String access_token;
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private static final String REFRESH_URI = "/api/user/refresh";
	
	@SuppressWarnings("unchecked")
	@Before
	public void setLogin() throws Exception {
		UserDto userDto = new UserDto("test1", "1234");
		String userJson = objectMapper.writeValueAsString(userDto);
		
		mockMvc.perform(post("/api/user/create")
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		
		MvcResult mvcResult = this.mockMvc.perform(post("/api/user/login")
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn();
		
		String content = mvcResult.getResponse().getContentAsString();
		RestReponseModel restReponseModel = objectMapper.readValue(content, RestReponseModel.class);
		Map<String, String> data = (Map<String, String>) restReponseModel.getData();
		
		access_token = data.get("access_token").toString();
	}
	
	@Test
	public void validRefreshToken() throws Exception {
		String oldToken = String.format("Bearer %s", access_token);
		
		ResultActions resultActions = this.mockMvc.perform(get(REFRESH_URI)
				.header(Constants.AUTHORIZATION, oldToken))
				.andDo(print());

		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.refresh_token").exists());
	}
	
	@Test
	public void emptyAuthorization() throws Exception {
		ResultActions resultActions = this.mockMvc.perform(get(REFRESH_URI)
				.header(Constants.AUTHORIZATION, "")).andDo(print());

		resultActions
			.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void emptyHeader() throws Exception {
		ResultActions resultActions = this.mockMvc.perform(get(REFRESH_URI)).andDo(print());
		resultActions
			.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void invalidAuthorization() throws Exception {
		String access_token = "unknownValue";
		
		ResultActions resultActions = this.mockMvc.perform(get(REFRESH_URI)
				.header(Constants.AUTHORIZATION, access_token)).andDo(print());

		resultActions
			.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void expiredAccessToken() throws Exception {
		Claims claims = tokenService.decodeToken(access_token);
		Date afterTime = DateUtil.getTwoHourExpireTimeMillis();
		Date expiredTime = claims.getExpiration();
		
		assertEquals(true, afterTime.after(expiredTime));
	}
}
