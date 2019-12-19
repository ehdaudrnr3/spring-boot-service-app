package com.spring.boot.service.app.region;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.spring.boot.service.app.constant.Constants;
import com.spring.boot.service.app.models.RegionDto;
import com.spring.boot.service.app.models.entity.Region;
import com.spring.boot.service.app.models.entity.RegionDetail;
import com.spring.boot.service.app.repository.RegionRepository;

public class RegionSaveTest extends RegionBaseTest {
	
	@Autowired
	private RegionRepository regionRepository;
	
	private static final String API_URI = "/api/region";
	
	@Test
	public void emptyAccessToken() throws Exception {
		this.mockMvc.perform(get(API_URI))
			.andDo(print())
			.andExpect(status().isInternalServerError());
    }
	
	@Test
    public void insertSuccess() throws Exception {
		RegionDto regionDto = RegionDto.builder()
				.region("동백시").target("동백시에 사는 사람").usage("걸어서").limit("10억원 이내")
				.rate("5%").institute("동백시").mgmt("동백지점").reception("동백시 소재 영업점").build();
		
		List<RegionDto> list = Arrays.asList(regionDto);
		String content = objectMapper.writeValueAsString(list);
		
		ResultActions actions = this.mockMvc.perform(post(API_URI)
				.header(Constants.AUTHORIZATION, access_token)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		
		actions.andExpect(status().isOk());
    }
	
	@Test
    public void updateSuccess() throws Exception {
		
		RegionDto insertRegionDto = RegionDto.builder()
				.region("동백시").target("동백시에 사는 사람").usage("걸어서").limit("10억원 이내")
				.rate("5%").institute("동백시").mgmt("동백지점").reception("동백시 소재 영업점").build();
		
		List<RegionDto> list = Arrays.asList(insertRegionDto);
		String content = objectMapper.writeValueAsString(list);
		
		this.mockMvc.perform(post(API_URI)
				.header(Constants.AUTHORIZATION, access_token)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		
		RegionDto updateRegionDto = RegionDto.builder()
				.region("동백시").target("동백시에 사는 사람").usage("운전").limit("10억원 이내")
				.rate("10%").institute("동백시").mgmt("동백지점").reception("동백시 소재 영업점").build();
		
		list = Arrays.asList(updateRegionDto);
		content = objectMapper.writeValueAsString(list);
		
		ResultActions actions = this.mockMvc.perform(post(API_URI)
				.header(Constants.AUTHORIZATION, access_token)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		
		actions
			.andExpect(status().isOk());
    }
	
	@Test
    public void missingRequiredForRegionName() throws Exception {
		RegionDto regionDto = RegionDto.builder().target("동백시에 사는 사람").usage("걸어서").limit("10억원 이내")
				.rate("5%").institute("동백시").mgmt("동백지점").reception("동백시 소재 영업점").build();
		
		List<RegionDto> list = Arrays.asList(regionDto);
		String content = objectMapper.writeValueAsString(list);
		
		ResultActions actions = this.mockMvc.perform(post(API_URI)
				.header(Constants.AUTHORIZATION, access_token)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		
		actions
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("지자체명이 입력되지 않았습니다."));
    }
	
	@Test
    public void updateLastModifiedDate() throws Exception {
		RegionDto insertRegionDto = RegionDto.builder()
				.region("동백시").target("동백시에 사는 사람").usage("걸어서").limit("10억원 이내")
				.rate("5%").institute("동백시").mgmt("동백지점").reception("동백시 소재 영업점").build();
		
		List<RegionDto> list = Arrays.asList(insertRegionDto);
		String content = objectMapper.writeValueAsString(list);
		
		this.mockMvc.perform(post(API_URI)
				.header(Constants.AUTHORIZATION, access_token)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
		
		Thread.sleep(5000);
		
		RegionDto updateRegionDto = RegionDto.builder()
				.region("동백시").target("동백시에 사는 사람").usage("운전").limit("20억원 이내")
				.rate("10%").institute("동백시청").mgmt("동백지점").reception("동백시 소재 영업점").build();
		
		list = Arrays.asList(updateRegionDto);
		content = objectMapper.writeValueAsString(list);
		
		this.mockMvc.perform(post(API_URI)
				.header(Constants.AUTHORIZATION, access_token)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
		
		Region region = regionRepository.findByRegionName("동백시");
		RegionDetail regionDetail = region.getRegionDetail();
		
		boolean afterUpdate = regionDetail.getLastModifiedDate().after(regionDetail.getCreatedDate());
		assertEquals(true, afterUpdate);
    }
	
	@Test
    public void emptyBody() throws Exception {
		ResultActions actions = this.mockMvc.perform(post(API_URI)
				.header(Constants.AUTHORIZATION, access_token)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		
		actions
			.andExpect(status().isBadRequest());
    }
}
