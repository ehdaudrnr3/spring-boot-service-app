package com.spring.boot.service.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.spring.boot.service.app.constant.MessageConstant;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User Not Found")
public class UserNotFoundException extends ApplicationRuntimeException {
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(HttpStatus status, String message) {
		super(status, message);
	}
	
	public static UserNotFoundException getUserNotFoundException() {
		return new UserNotFoundException(HttpStatus.NOT_FOUND, MessageConstant.USER_NOT_FOUND);
	}
}
