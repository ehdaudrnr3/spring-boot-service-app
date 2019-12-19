package com.spring.boot.service.app.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.boot.service.app.exception.ApplicationRuntimeException;
import com.spring.boot.service.app.exception.TokenException;
import com.spring.boot.service.app.exception.UserConflictException;
import com.spring.boot.service.app.exception.UserInvalidException;
import com.spring.boot.service.app.exception.UserNotFoundException;
import com.spring.boot.service.app.models.response.ErrorResponseModel;
import com.spring.boot.service.app.utils.DateUtil;

@RestControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler({
		ApplicationRuntimeException.class, UserConflictException.class, UserInvalidException.class, 
		UserNotFoundException.class, TokenException.class
	})
	public ResponseEntity<ErrorResponseModel> handleException(ApplicationRuntimeException e, HttpServletRequest request) {
		ErrorResponseModel errorModel = new ErrorResponseModel();
		errorModel.setTimestamp(DateUtil.getCurrentDateTime());
		errorModel.setPath(request.getRequestURI());
		errorModel.setStatus(e.getStatus().value());
		errorModel.setMessage(e.getMessage());
		errorModel.setError(e.getStatus().getReasonPhrase());
		
		return new ResponseEntity<ErrorResponseModel>(errorModel, e.getStatus());
	}
}
