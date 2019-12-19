package com.spring.boot.service.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.boot.service.app.exception.ApplicationRuntimeException;
import com.spring.boot.service.app.exception.UserNotFoundException;
import com.spring.boot.service.app.models.entity.User;
import com.spring.boot.service.app.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			User user = userRepository.findById(email).orElse(null);
			if(user == null) {
				throw UserNotFoundException.getUserNotFoundException();
			}
			return user;
		} catch (ApplicationRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
}
