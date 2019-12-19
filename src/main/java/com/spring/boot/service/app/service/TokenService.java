package com.spring.boot.service.app.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.spring.boot.service.app.constant.Constants;
import com.spring.boot.service.app.models.UserDto;
import com.spring.boot.service.app.utils.DateUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	/**
	 * 토큰 생성
	 * @param userDto
	 * @return
	 * @throws Exception
	 */
	public String createToken(UserDto userDto) throws Exception {
		try {
			Date expireTime = DateUtil.getOneHourExpireTimeMillis();
			String token = Jwts.builder()
						.setHeaderParam("typ", "JWT")
						.setHeaderParam("createDate", System.currentTimeMillis())
						.setSubject(userDto.getEmail())
						.claim("user", userDto)
						.setExpiration(expireTime)
						.signWith(SignatureAlgorithm.HS256, Constants.SECRET_KEY)
						.compact();
			
			return token;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 토큰 검증
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public boolean isUsable(String token) throws Exception {
		try {
			Jwts.parser().setSigningKey(Constants.SECRET_KEY).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 토큰 디코드
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public Claims decodeToken(String token) throws Exception {
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(Constants.SECRET_KEY)
					.parseClaimsJws(token).getBody();
			return claims;
		} catch (Exception e) {
			throw e;
		}
	}
}
