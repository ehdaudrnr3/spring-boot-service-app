package com.spring.boot.service.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.spring.boot.service.app.constant.MessageConstant;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Invalid Token")
public class TokenException extends ApplicationRuntimeException {
	private static final long serialVersionUID = 1L;
	
	public TokenException(HttpStatus status, String message) {
		super(status, message);
	}
	
	public static TokenException getInvalidTokenException(){
        return new TokenException(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstant.INVALID_TOKEN);
    }
	
	public static TokenException getExpiredTokenException(){
        return new TokenException(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstant.EXPIRED_TOKEN);
    }
	
	public static TokenException getNullOrEmptyTokenException(){
        return new TokenException(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstant.EMPTY_TOKEN);
    }
}
