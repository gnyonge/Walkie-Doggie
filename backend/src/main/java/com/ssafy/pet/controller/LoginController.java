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
import com.ssafy.pet.util.UidUtil;

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

	@Autowired
	private UidUtil uidutil;

	// 카카오 로그인
	@ApiOperation(value = "KAKAO LOGIN", notes = "카카오 로그인")
	@GetMapping("/kakao")
	public ResponseEntity<Map<String, Object>> kakao_login(@RequestParam String code, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 카카오 로그인 시작");
			String access_Token = kakao.getAccessToken(code);

			Map<String, Object> userInfo = kakao.getUserInfo(access_Token);
			System.out.println("login Controller : " + userInfo);
			// 닉네임이랑 이메일만 가져옵니다 저는

			System.out.println(userInfo.get("email"));
			String email = (String) userInfo.get("email");
			String nickname = (String) userInfo.get("nickname");

			UserDto kakaoCome = loginservice.check_email(email);
			System.out.println(kakaoCome);
			if (kakaoCome != null && kakaoCome.getU_password() != null) {
				resultMap.put("message", "회원가입이 되어있는 이메일입니다.");

			} else {
				// 처음온애니? 이미 있는 친구니?
				if (kakaoCome == null) {
					// 처음온 친구니까
					logger.info("처음 카카오로 로그인");

					kakaoCome = new UserDto(uidutil.MakeUid(), email, nickname);
					int result = loginservice.insert_kakao(kakaoCome);
				} else {
					// 아닌 친구니까
					logger.info("기존 카카오로 로그인헀던 회원");
				}
				String token = jwtutil.create(kakaoCome);
				res.setHeader("doggie_token", token);
				logger.info("토큰: {}", token);
				resultMap.put("doggie_token", token);
				resultMap.put("user", kakaoCome);
				resultMap.put("message", "로그인에 성공하였습니다.");
			}
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("카카오 로그인 등록 실패 : {}", e);
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

			if (check_user.getU_email().equals(user.getU_email())
					&& check_user.getU_password().equals(user.getU_password())) {
				check_user.setU_password(null);
				// 로그인이 맞다는거자나 그럼 토큰을 생성하자
				String token = jwtutil.create(check_user);
				res.setHeader("doggie_token", token);
				logger.info("토큰: {}", token);
				resultMap.put("doggie_token", token);
				resultMap.put("user", check_user);
				resultMap.put("message", "로그인에 성공하였습니다.");
			} else {
				resultMap.put("message", "로그인에 실패하였습니다.");
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
