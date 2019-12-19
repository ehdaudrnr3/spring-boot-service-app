package com.spring.boot.service.app.models.response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.boot.service.app.utils.DateUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ResponseModel {
	private String timestamp;
	private String path;
	private Integer status;
	
	public ResponseModel(HttpServletRequest request, HttpServletResponse response) {
		this.setTimestamp(DateUtil.getCurrentDateTime());
		this.setPath(request.getRequestURI());
		this.setStatus(response.getStatus());
	}
}
