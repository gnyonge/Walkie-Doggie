package com.ssafy.pet.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pet.util.KakaoUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("LoginController V1")
@RestController
@RequestMapping("/login")
public class LoginController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private KakaoUtil kakao;
	
	// 카카오 로그인
		@ApiOperation(value = "KAKAO LOGIN", notes = "카카오 로그인")
		@GetMapping("/kakao")
		public ResponseEntity<Map<String, Object>> kakao_login(@RequestParam String code) {
			Map<String, Object> resultMap = new HashMap<>();
			HttpStatus status = null;

			try {
				logger.info("=====> 카카오 로그인 시작");
				System.out.println(code);
				String access_Token = kakao.getAccessToken(code);
				System.out.println("access_token : " + access_Token);
			
				Map<String, Object> userInfo = kakao.getUserInfo(access_Token);
			    System.out.println("login Controller : " + userInfo);
			    
//			    String nickname = userInfo.get("nickname");
//			    String email = userInfo.get("email");
			    
			    
			    //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
			    if (userInfo.get("email") != null) {
//			        session.setAttribute("userId", userInfo.get("email"));
//			        session.setAttribute("access_Token", access_Token);
			    }
				
				
				int result = 0;

				if (result == 1) {
					logger.info("=====> 커뮤니티 글 등록 성공");
					resultMap.put("message", "게시글 등록에 성공하였습니다.");
					status = HttpStatus.ACCEPTED;
				} else {
					logger.info("=====> 커뮤니티 글 등록 실패");
					resultMap.put("message", "게시글 등록에 실패하였습니다.");
					status = HttpStatus.NOT_FOUND;
				}

			} catch (Exception e) {
				// TODO: handle exception
				logger.error("글 등록 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			return new ResponseEntity<Map<String, Object>>(resultMap, status);
		}

}
