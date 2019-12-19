package com.spring.boot.service.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.spring.boot.service.app.constant.MessageConstant;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Invalid User")
public class UserInvalidException extends ApplicationRuntimeException {
	private static final long serialVersionUID = 1L;

	public UserInvalidException(HttpStatus status, String message) {
		super(status, message);
	}
	
	public static UserInvalidException getUserInvalidException() {
		return new UserInvalidException(HttpStatus.FORBIDDEN, MessageConstant.INVALID_USER);
	}
}
