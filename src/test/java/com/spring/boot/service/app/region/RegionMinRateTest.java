package com.spring.boot.service.app.region;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.spring.boot.service.app.constant.Constants;

public class RegionMinRateTest extends RegionBaseTest {
	 
	private static final String API_URI = "/api/region/min-rate";
	
	@Test
	public void emptyAccessToken() throws Exception {
		this.mockMvc.perform(get(API_URI))
			.andDo(print())
			.andExpect(status().isInternalServerError());
    }
	
	@Test
    public void getSuccess() throws Exception {
		ResultActions actions = this.mockMvc.perform(get(API_URI)
				.header(Constants.AUTHORIZATION, access_token))
				.andDo(print());
		
		actions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.institute").exists());
    }
}
