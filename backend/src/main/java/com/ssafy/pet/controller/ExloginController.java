package com.ssafy.pet.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;


public class ExloginController {
	
	@GetMapping("/auth/kakao/callback")
	public @ResponseBody ResponseEntity<String> kakaoCallback(String code) {
		RestTemplate rt = new RestTemplate();
		
		//HttpHeader 오브젝트 생성 
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "1bf3f0e4ba92eceb2527659918098b46");
		params.add("redirect_uri", "http://localhost:8888/login/kakao");
		params.add("code", code);
		params.add("client_secret", "HVjE2cjBAouB82GQXNhYSeZkc0M07dD8");
		
		//body와 header을 가진 엔티티가 되 
		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기 왜 하나에 담냐면 exchange 가 하나의 오브젝트를 담아야해서
		HttpEntity<MultiValueMap<String , String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
		
		//Http요청하기 - Post방식으로 - response 변수의 응답을 받음
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token", //토큰 발급 요청 주소
				HttpMethod.POST,//요청 메서드가 뭔지
				kakaoTokenRequest, // 데이터
				String.class ///응답을 받을 타입 response는 스트링타입으로 응답 바등ㄹ꺼래
				);
		
		//response.getBody(), getHeader() 로 볼수있는데 body만 보면돼 
		
		ObjectMapper objectMapper = new ObjectMapper();
//		OAuthtoken
		
		
		return response;
	}

}
