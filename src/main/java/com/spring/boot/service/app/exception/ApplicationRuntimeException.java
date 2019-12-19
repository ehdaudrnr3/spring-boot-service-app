package com.spring.boot.service.app.exception;

import org.springframework.http.HttpStatus;

public class ApplicationRuntimeException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	private HttpStatus status;

	public ApplicationRuntimeException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
}
