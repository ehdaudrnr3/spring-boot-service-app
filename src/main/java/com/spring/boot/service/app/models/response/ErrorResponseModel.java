package com.spring.boot.service.app.models.response;

import javax.servlet.http.HttpServletRequest;

import com.spring.boot.service.app.exception.ApplicationRuntimeException;
import com.spring.boot.service.app.utils.DateUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ErrorResponseModel extends ResponseModel {
	private String error;
	private String message;
	
	public ErrorResponseModel(ApplicationRuntimeException e, HttpServletRequest request) {
		this.setTimestamp(DateUtil.getCurrentDateTime());
		this.setPath(request.getRequestURI());
		this.setStatus(e.getStatus().value());
		this.setMessage(e.getMessage());
		this.setError(e.getStatus().getReasonPhrase());
	}
}
