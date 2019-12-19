package com.spring.boot.service.app.region;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.spring.boot.service.app.constant.Constants;

public class RegionDeleteTest extends RegionBaseTest {
	
	private static final String API_URI = "/api/region";
	
	@Test
    public void emptyAccessToken() throws Exception {
		this.mockMvc.perform(delete(API_URI))
			.andDo(print())
			.andExpect(status().isInternalServerError());
    }
	
	@Test
    public void deleteSuccess() throws Exception {
		ResultActions actions = this.mockMvc.perform(delete(API_URI)
				.header(Constants.AUTHORIZATION, access_token)
				.param("region", "강릉시"))
				.andDo(print());
				
		actions
			.andExpect(status().isOk());
    }
	
	@Test
    public void deleteNotFound() throws Exception {
		ResultActions actions = this.mockMvc.perform(delete(API_URI)
				.header(Constants.AUTHORIZATION, access_token)
				.param("region", "동백시"))
				.andDo(print());
			
		actions
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.message").value("지자체 정보를 찾을 수 없습니다."));
    }
	
	@Test
    public void emptyParameter() throws Exception {
		ResultActions actions = this.mockMvc.perform(delete(API_URI)
				.header(Constants.AUTHORIZATION, access_token))
				.andDo(print());
		
		actions
			.andExpect(status().isBadRequest());
    }
}
