package com.spring.boot.service.app.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.spring.boot.service.app.constant.Constants;
import com.spring.boot.service.app.exception.TokenException;
import com.spring.boot.service.app.service.TokenService;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtInterceptor implements HandlerInterceptor {
	
	@Autowired
	private TokenService tokenService;
	
	private static String REFRESH_TOKEN_URI = "/api/user/refresh";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader(Constants.AUTHORIZATION);
		try {
			if(request.getRequestURI().equals(REFRESH_TOKEN_URI)) {
				if(!token.startsWith("Bearer ") || token.length() < 7) {
		    		throw TokenException.getInvalidTokenException();
		    	}
				token = token.substring(7);
			} 

			return tokenService.isUsable(token);
		} catch (ExpiredJwtException e) {
			throw TokenException.getExpiredTokenException();
		} catch (IllegalArgumentException e) {
			throw TokenException.getNullOrEmptyTokenException();
		} catch (NullPointerException e) {
			throw TokenException.getNullOrEmptyTokenException();
		} catch (Exception e) {
			throw TokenException.getInvalidTokenException();
		}
	}
}
