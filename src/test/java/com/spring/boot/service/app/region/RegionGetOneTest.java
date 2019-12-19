package com.spring.boot.service.app.region;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.spring.boot.service.app.constant.Constants;

public class RegionGetOneTest extends RegionBaseTest {
	
	private static final String API_URI = "/api/region";
	
	@Test
    public void emptyAccessToken() throws Exception {
		this.mockMvc.perform(get(API_URI))
			.andDo(print())
			.andExpect(status().isInternalServerError());
    }
	
	@Test
    public void getSuccess() throws Exception {
		ResultActions actions = this.mockMvc.perform(get(API_URI)
				.header(Constants.AUTHORIZATION, access_token)
				.param("region", "강릉시"))
				.andDo(print());
		
		actions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.rate").value("3%"));
    }
	
	@Test
    public void notFoundRegion() throws Exception {
		ResultActions actions = this.mockMvc.perform(get(API_URI)
				.header(Constants.AUTHORIZATION, access_token)
				.param("region", "통백시"))
				.andDo(print());
		
		actions
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.message").value("지자체 정보를 찾을 수 없습니다."));
    }
	
	@Test
    public void emptyParameter() throws Exception {
		this.mockMvc.perform(get(API_URI)
				.header(Constants.AUTHORIZATION, access_token))
				.andDo(print())
				.andExpect(status().isBadRequest());
    }
}
