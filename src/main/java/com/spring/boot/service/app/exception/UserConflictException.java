package com.spring.boot.service.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.spring.boot.service.app.constant.MessageConstant;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Conflict")
public class UserConflictException extends ApplicationRuntimeException {
	private static final long serialVersionUID = 1L;

	public UserConflictException(HttpStatus status, String message) {
		super(status, message);
	}
	
	public static UserConflictException getUserConfiltException(){
        return new UserConflictException(HttpStatus.CONFLICT, MessageConstant.USER_CONFLICT);
    }
}
