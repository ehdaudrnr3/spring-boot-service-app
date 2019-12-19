package com.spring.boot.service.app.region;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Map;

import org.junit.Before;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.service.app.models.UserDto;
import com.spring.boot.service.app.models.response.RestReponseModel;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class RegionBaseTest {
	
	@Autowired
	protected MockMvc mockMvc;
	
	protected String access_token;

	protected ObjectMapper objectMapper = new ObjectMapper();
	
	@SuppressWarnings("unchecked")
	@Before
	public void setLogin() throws Exception {
		UserDto userDto = new UserDto("regionTest", "1234");
		String userJson = objectMapper.writeValueAsString(userDto);

		this.mockMvc.perform(post("/api/user/create")
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print());
		
		MvcResult mvcResult = this.mockMvc.perform(post("/api/user/login")
				.content(userJson)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn();
		
		String content = mvcResult.getResponse().getContentAsString();
		RestReponseModel restReponseModel = objectMapper.readValue(content, RestReponseModel.class);
		Map<String, String> data = (Map<String, String>) restReponseModel.getData();

		access_token = data.get("access_token").toString();
	}
	
	//토큰 만료시 예외처리
}
