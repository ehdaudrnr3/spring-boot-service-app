package com.spring.boot.service.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.service.app.constant.Constants;
import com.spring.boot.service.app.models.UserDto;
import com.spring.boot.service.app.models.response.RestReponseModel;
import com.spring.boot.service.app.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create")
    public ResponseEntity<RestReponseModel> create(@RequestBody(required = true) @Valid UserDto userDto, HttpServletRequest request, HttpServletResponse response) throws Exception{
        userService.create(userDto);
        userDto.setPassword(null);
        
        RestReponseModel responseModel = new RestReponseModel(request, response, userDto);
        return new ResponseEntity<RestReponseModel>(responseModel, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<RestReponseModel> login(
    	@RequestBody(required = true) @Valid UserDto userDto, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	try {
    		Map<String, Object> map = userService.login(userDto);
        	response.setHeader(Constants.AUTHORIZATION, map.get("access_token").toString());
        	
    		RestReponseModel restReponseModel = new RestReponseModel(request, response, map);
        	return new ResponseEntity<RestReponseModel>(restReponseModel, HttpStatus.OK);
		} catch (Exception e) {
			throw e;
		}
    }
    
    @GetMapping("/refresh")
    public ResponseEntity<RestReponseModel> refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	try {
    		String oldToken = request.getHeader(Constants.AUTHORIZATION).substring(7);
        	Map<String, Object> map = userService.refreshToken(oldToken);
        	
        	RestReponseModel restReponseModel = new RestReponseModel(request, response, map);
        	return new ResponseEntity<RestReponseModel>(restReponseModel, HttpStatus.OK);
		} catch (Exception e) {
			throw e;
		}
    }
    
    @GetMapping("/list")
	public ResponseEntity<RestReponseModel> getUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	try {
    		List<UserDto> userList = userService.getUserList();
        	RestReponseModel restReponseModel = new RestReponseModel(request, response, userList);
        	return new ResponseEntity<RestReponseModel>(restReponseModel, HttpStatus.OK);
		} catch (Exception e) {
			throw e;
		}
	}
}
