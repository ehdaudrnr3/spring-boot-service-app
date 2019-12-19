package com.spring.boot.service.app.models.response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.boot.service.app.utils.DateUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class RestReponseModel extends ResponseModel {
	private Object data;
	
	public RestReponseModel(HttpServletRequest request, HttpServletResponse response, Object data) {
		this.setTimestamp(DateUtil.getCurrentDateTime());
		this.setPath(request.getRequestURI());
		this.setData(data);
		this.setStatus(response.getStatus());
	}
}
