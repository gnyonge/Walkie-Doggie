package com.ssafy.pet.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ssafy.pet.dto.UserDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	public static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);

	private static final String TK = "WALKIEDOGGIETOKEN";
	private static final long EXPIRE_MINUTES = 30 * 60 * 1000L; // 30분

	// 로그인 성공시 사용자 정보를 기반으로 JWTToken을 생성하여 반환.
	// JWT Token = Header + Payload + Signature
	public String create(UserDto user) {
		JwtBuilder jwtBuilder = Jwts.builder();

		// Header 설정
		jwtBuilder.setHeaderParam("typ", "JWT"); // 토큰의 타입으로 고정 값.

		// Payload 설정 - claim 정보 포함
		jwtBuilder.setSubject("doggieToken") // 토큰의 제목 설정
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * EXPIRE_MINUTES)) // 유효기간
				.claim("user", user) // 정보 저장
				.claim("message", "환영합니다. "); // 담고 싶은 정보 설정.

		// signature 설정 - secret key를 이용한 암호화
		jwtBuilder.signWith(SignatureAlgorithm.HS256, this.generateKey());

		// 마지막 직렬화 처리
		String jwt = jwtBuilder.compact();
		logger.info("jwt : {}", jwt);
		return jwt;
	}

	//TK를 getBytes() 처리
	private byte[] generateKey() {
		byte[] key = null;
		try {
			key = TK.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if (logger.isInfoEnabled()) {
				e.printStackTrace();
			} else {
				logger.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}

		return key;
	}

	// 전달 받은 토큰이 제대로 생성된것이니 확인 하고 문제가 있다면 RuntimeException을 발생.
	//JWTinterceptor에서 사용!
	public void checkValid(String jwt) {
		// 예외가 발생하지 않으면 OK
		logger.info("토큰 점검: {}", jwt);
		Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
	}

	// JWT Token을 분석해서 필요한 정보를 반환.
	public Map<String, Object> get(String jwt) {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
		} catch (final Exception e) {
			throw new RuntimeException();
		}

		logger.info("claims : {}", claims);
		// Claims는 Map의 구현체이다.
		return claims.getBody();
	}
	
	public String getUserId(String jwt) {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
		} catch (final Exception e) {
			throw new RuntimeException();
		}

		Map<String, Object> value = claims.getBody();
		logger.info("value : {}", value);

		return (String) value.get("uid");
	}

}
