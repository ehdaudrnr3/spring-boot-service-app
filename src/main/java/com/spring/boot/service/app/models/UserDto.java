package com.spring.boot.service.app.models;

import javax.validation.constraints.NotBlank;

import com.spring.boot.service.app.models.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class UserDto {
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
	
	public UserDto(User user) {
		this.email = user.getEmail();
	}
}
