package com.spring.boot.service.app.user;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.service.app.constant.Constants;
import com.spring.boot.service.app.models.UserDto;
import com.spring.boot.service.app.models.entity.User;
import com.spring.boot.service.app.models.response.RestReponseModel;
import com.spring.boot.service.app.repository.UserRepository;
import com.spring.boot.service.app.service.TokenService;
import com.spring.boot.service.app.utils.DateUtil;

import io.jsonwebtoken.Claims;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserListTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private TokenService tokenService;
	
	private String access_token;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@PostConstruct
	public void saveUser() throws Exception {
		User user1 = new User("test1", "1234");
		user1.setPassword(passwordEncoder.encode(user1.getPassword()));
		
		User user2 = new User("test2", "1235");
		user2.setPassword(passwordEncoder.encode(user2.getPassword()));
		
		User user3 = new User("test3", "1236");
		user3.setPassword(passwordEncoder.encode(user3.getPassword()));
		
		User user4 = new User("test4", "1237");
		user4.setPassword(passwordEncoder.encode(user4.getPassword()));
		
		List<User> userList = Arrays.asList(user1, user2, user3, user4);
		userRepository.saveAll(userList);
	}
	
	@SuppressWarnings("unchecked")
	@Before
	public void setLogin() throws Exception {
		UserDto userDto = new UserDto("test1", "1234");
		String userJson = objectMapper.writeValueAsString(userDto);
		
		MvcResult mvcResult = this.mockMvc.perform(post("/api/user/login")
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn();
		
		String content = mvcResult.getResponse().getContentAsString();
		RestReponseModel restReponseModel = objectMapper.readValue(content, RestReponseModel.class);
		Map<String, String> data = (Map<String, String>) restReponseModel.getData();
		access_token = data.get("access_token").toString();
	}
	
	@Test
	public void userListTest() throws Exception {
		ResultActions resultActions = this.mockMvc.perform(get("/api/user/list")
				.header(Constants.AUTHORIZATION, access_token)).andDo(print());
		
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data", hasSize(4)));
  	}
	
	@Test
	public void expiredAccessToken() throws Exception {
		Claims claims = tokenService.decodeToken(access_token);
		Date afterTime = DateUtil.getTwoHourExpireTimeMillis();
		Date expiredTime = claims.getExpiration();
		
		assertEquals(true, afterTime.after(expiredTime));
	}
}
