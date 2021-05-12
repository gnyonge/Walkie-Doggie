package com.ssafy.pet.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pet.dto.UserDto;
import com.ssafy.pet.service.LoginService;
import com.ssafy.pet.util.JWTUtil;
import com.ssafy.pet.util.KakaoUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("LoginController V1")
@RestController
@RequestMapping("/login")
public class LoginController {
	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private KakaoUtil kakao;
	
	@Autowired
	private LoginService loginservice;
	
	@Autowired
	private JWTUtil jwtutil;
	
	// 카카오 로그인
	@ApiOperation(value = "KAKAO LOGIN", notes = "카카오 로그인")
	@GetMapping("/kakao")
	public ResponseEntity<Map<String, Object>> kakao_login(@RequestParam String code, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 카카오 로그인 시작");
			System.out.println(code);
			String access_Token = kakao.getAccessToken(code);
			System.out.println("access_token : " + access_Token);
		
			Map<String, Object> userInfo = kakao.getUserInfo(access_Token);
		    System.out.println("login Controller : " + userInfo);
		    
		    System.out.println(userInfo.get("email"));
		    String email = (String) userInfo.get("email");
		    
		    
		    UserDto kakaoCome = loginservice.check_email(email);
		    if(kakaoCome==null) {
		    	//한번도 와번적이 없는 친구
//		    	int res = loginservice.insert_kakao(email);
		    }else {
		    	//이미 우리 회원이시네용~
		    	kakaoCome.setU_password(null);
		    }
			String token = jwtutil.create(kakaoCome);
			res.setHeader("doggie_token", token);
			logger.info("토큰: {}", token);
			resultMap.put("doggie_token", token);
			resultMap.put("user", kakaoCome);
			resultMap.put("message","로그인에 성공하였습니다.");
			status = HttpStatus.ACCEPTED;

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("자체로그인 등록 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	// 자체 로그인
	@ApiOperation(value = "SYSTEM LOGIN", notes = "자체 로그인")
	@PostMapping("/signin")
	public ResponseEntity<Map<String, Object>> our_login(@RequestBody UserDto user, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 자체 로그인 시작 our~our~our~login~our~site~our web site~");
			UserDto check_user = loginservice.check_email(user.getU_email());
			
			if(check_user.getU_email().equals(user.getU_email())&&check_user.getU_password().equals(user.getU_password())) {
				check_user.setU_password(null); 
				//로그인이 맞다는거자나 그럼 토큰을 생성하자
				String token = jwtutil.create(check_user);
				res.setHeader("doggie_token", token);
				logger.info("토큰: {}", token);
				resultMap.put("doggie_token", token);
				resultMap.put("user", check_user);
				resultMap.put("message","로그인에 성공하였습니다.");
			}else {
				resultMap.put("message","로그인에 실패하였습니다.");				
			}
			status = HttpStatus.ACCEPTED;

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("자체 로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
		
		
		

}
