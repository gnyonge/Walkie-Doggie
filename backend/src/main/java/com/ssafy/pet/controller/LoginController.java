package com.ssafy.pet.controller;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.servlet.view.RedirectView;

import com.ssafy.pet.dto.PetDto;
import com.ssafy.pet.dto.UserDto;
import com.ssafy.pet.service.LoginService;
import com.ssafy.pet.service.UserService;
import com.ssafy.pet.util.JWTUtil;
import com.ssafy.pet.util.SecurityUtil;
import com.ssafy.pet.util.UidUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("LoginController V1")
@RestController
@RequestMapping("/login")
public class LoginController {
	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginservice;
	
	@Autowired
	private UserService userservice;

	@Autowired
	private JWTUtil jwtutil;

	@Autowired
	private UidUtil uidutil;

	@Autowired
	private SecurityUtil securityutil;

	// 자체 로그인
	@ApiOperation(value = "SYSTEM LOGIN", notes = "자체 로그인")
	@PostMapping("/signin")
	public ResponseEntity<Map<String, Object>> our_login(@RequestBody UserDto user, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 자체 로그인 시작");
			UserDto check_user = loginservice.check_email(user.getU_email());

			if (check_user == null) {
				resultMap.put("message", "아이디나 비밀번호를 확인해주세요");
			} else {
				// 아이디나 비밀번호는 있단 ㅇㅒ기군요?
				String checkPass = check_user.getU_password();
				String UserPass = securityutil.bytesToHex(securityutil.sha256(user.getU_password()));
				if (check_user.getU_email().equals(user.getU_email()) && checkPass.equals(UserPass)
						&& check_user.getU_flag() == 0) {
					// 로그인이 맞다는거자나 그럼 토큰을 생성하자
					String token = jwtutil.create(check_user);
					res.setHeader("doggieToken", token);
					// logger.info("토큰: {}", token);
					resultMap.put("doggieToken", token);
					resultMap.put("user", check_user);
					resultMap.put("message", "로그인에 성공하였습니다.");
				} else {
					resultMap.put("message", "아이디나 비밀번호를 확인해주세요");
				}
			}
			status = HttpStatus.ACCEPTED;
		} catch (

		Exception e) {
			// TODO: handle exception
			logger.error("자체 로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "VUE KAKAO LOGIN", notes = "카카오 로그인 처리")
	@PostMapping("/ksign")
	public ResponseEntity<Map<String, Object>> vue_login(@RequestBody UserDto user, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 카카오 정보 로그인 시작");
			UserDto check_user = loginservice.check_email(user.getU_email());

			if (check_user != null && check_user.getU_password() != null) {
				resultMap.put("message", "회원가입이 되어있는 이메일입니다.");

			} else {
				if (check_user == null) {
					// 처음온 친구구나?
					logger.info("처음 카카오 로그인");

					String uid = uidutil.MakeUid();
					check_user = new UserDto(uid, user.getU_email(), user.getU_nickname());
					int result = loginservice.insert_kakao(check_user);
					resultMap.put("message", "1");
				} else {
					// 이미 회원이시군요?
					logger.info("기존 카카오로 로그인헀던 회원");
					
					List<PetDto> list = userservice.petInfo(check_user.getUid());
					resultMap.put("list", list);
					resultMap.put("message", "2");
					
					
				}
				String token = jwtutil.create(check_user);
				res.setHeader("doggieToken", token);
				logger.info("토큰: {}", token);
				resultMap.put("doggieToken", token);
				resultMap.put("user", check_user);
			}
			status = HttpStatus.ACCEPTED;
		} catch (

		Exception e) {
			// TODO: handle exception
			logger.error("자체 로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
