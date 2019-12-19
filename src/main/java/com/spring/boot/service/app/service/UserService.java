package com.spring.boot.service.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.service.app.constant.Constants;
import com.spring.boot.service.app.exception.ApplicationRuntimeException;
import com.spring.boot.service.app.exception.UserConflictException;
import com.spring.boot.service.app.models.UserDto;
import com.spring.boot.service.app.models.entity.User;
import com.spring.boot.service.app.repository.UserRepository;
import com.spring.boot.service.app.security.AuthProvider;

import io.jsonwebtoken.Claims;

@Service
public class UserService {
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthProvider authProvider;
	
	@Autowired
	private TokenService tokenService;
	
	/**
	 * 사용자 등록
	 * @param userDto
	 * @return
	 * @throws Exception
	 */
	public UserDto create(UserDto userDto) throws Exception {
		try {
			String encriptPassword = passwordEncoder.encode(userDto.getPassword());
			userDto.setPassword(encriptPassword);
			
			User user = new User(userDto.getEmail(), userDto.getPassword());
			boolean hasUser = userRepository.findById(user.getEmail()).isPresent();
			if(hasUser) {
				throw UserConflictException.getUserConfiltException();
			}
			userRepository.save(user);
			return userDto;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 로그인
	 * @param userDto
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> login(UserDto userDto) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
			Authentication authentication = authProvider.authenticate(authToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			String token = tokenService.createToken(userDto);
			Claims claims = tokenService.decodeToken(token);
			
			map.put("access_token", token);
			map.put("token_type", Constants.TOKEN_TYPE);
			map.put("expiration", claims.get("exp"));
        	
			return map;
		} catch(ApplicationRuntimeException e) {
			throw e;
		}
	}
	
	/**
	 * 새로운 토큰 발급
	 * @param oldToken
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> refreshToken(String oldToken) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			Claims claims = tokenService.decodeToken(oldToken);
	    	ObjectMapper objectMapper = new ObjectMapper();
	    	UserDto userDto = objectMapper.convertValue(claims.get("user"), UserDto.class);
	    	String token = tokenService.createToken(userDto);
	    	
	    	map.put("refresh_token", token);
	    	map.put("token_type", Constants.TOKEN_TYPE);
	    	map.put("expiration", claims.get("exp"));
	    	return map;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 사용자 목록
	 * @return
	 * @throws Exception
	 */
	public List<UserDto> getUserList() throws Exception {
		try {
			return userRepository.findAll().stream().map(UserDto::new).collect(Collectors.toList());
		} catch (Exception e) {
			throw e;
		}
	}
}
