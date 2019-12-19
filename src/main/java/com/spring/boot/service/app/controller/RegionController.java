package com.spring.boot.service.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.service.app.exception.ApplicationRuntimeException;
import com.spring.boot.service.app.models.RegionDto;
import com.spring.boot.service.app.models.response.ResponseModel;
import com.spring.boot.service.app.models.response.RestReponseModel;
import com.spring.boot.service.app.service.RegionService;

@RestController
@RequestMapping("/api/region")
public class RegionController {
	
	@Autowired
	private RegionService regionService;
	
	@GetMapping("/list") 
	public ResponseEntity<ResponseModel> getRegionList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<RegionDto>	list = regionService.getRegionList();
	 		RestReponseModel responseModel = new RestReponseModel(request, response, list);
			return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
			
		} catch (ApplicationRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@GetMapping() 
	public ResponseEntity<RestReponseModel> getRegion(
			@RequestParam(name = "region", required = true) String region,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			RegionDto regionDto = regionService.getRegion(region);
	 		RestReponseModel restReponseModel = new RestReponseModel(request, response, regionDto);
			return new ResponseEntity<RestReponseModel>(restReponseModel, HttpStatus.OK);
		} catch (ApplicationRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@PostMapping() 
	public ResponseEntity<ResponseModel> save(
			@RequestBody(required = true) List<RegionDto> regionList, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			regionService.save(regionList);
			ResponseModel responseModel = new ResponseModel(request, response);
			return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
		} catch (ApplicationRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@DeleteMapping()
	public ResponseEntity<ResponseModel> delete(@RequestParam(name = "region", required = true) String region, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			regionService.delete(region);
			ResponseModel responseModel = new ResponseModel(request, response);
			return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
		} catch (ApplicationRuntimeException e) {
			throw e;
		} catch (Exception e) {  
			throw e;
		}
	}
	
	@GetMapping("/desc") 
	public ResponseEntity<RestReponseModel> getListOrderByDesc(
			@RequestParam(name = "size", required = true) String size, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Map<String, String>> list = new ArrayList<>();
		try {
			int convertSize = Integer.parseInt(size);
			list = regionService.getRegionListOrderbyDesc(convertSize);
			
	 		RestReponseModel restReponseModel = new RestReponseModel(request, response, list);
			return new ResponseEntity<RestReponseModel>(restReponseModel, HttpStatus.OK);
		} catch (ApplicationRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@GetMapping("/min-rate") 
	public ResponseEntity<RestReponseModel> getInstituteByMinRate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, String> institutue = regionService.getInstituteByMinRate();
			
	 		RestReponseModel restReponseModel = new RestReponseModel(request, response, institutue);
			return new ResponseEntity<RestReponseModel>(restReponseModel, HttpStatus.OK);
		} catch (ApplicationRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
}