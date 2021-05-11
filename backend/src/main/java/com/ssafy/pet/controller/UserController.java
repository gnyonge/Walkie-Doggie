package com.ssafy.pet.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.pet.dto.PetDto;
import com.ssafy.pet.dto.UserDto;
import com.ssafy.pet.service.UserService;
import com.ssafy.pet.util.JWTUtil;
import com.ssafy.pet.util.MailUtils;
import com.ssafy.pet.util.S3Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("UserController V1")
@RestController
@RequestMapping("/user")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userservice;

	@Autowired
	private S3Util s3util;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private JWTUtil jwtutil;

	private String MakeUid() {
		StringBuffer made = new StringBuffer();

		for (int i = 0; i < 6; i++) {
			char a = (char) ((Math.random() * 26) + 97); // 소문자
			int ann = (int) (Math.random() * 9) + 1; // 숫자
			made.append(a);
			made.append(ann);
		}

		char b = (char) ((Math.random() * 26) + 97);
		made.append(b);
		String line = made.toString();
		return line;
	}

	// 회원가입하기
	@ApiOperation(value = "User Signup", notes = "자체로그인 회원가입")
	@PostMapping("/signup")
	public ResponseEntity<Map<String, Object>> signup(@RequestBody UserDto user) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 자체 회원가입 시작");
			user.setUid(MakeUid());

			int result = userservice.signup(user);

			if (result == 1) {
				logger.info("=====> 자체회원가입 가능");
				resultMap.put("message", "회원가입에 성공하였습니다.");
				status = HttpStatus.ACCEPTED;
			} else {
				logger.info("=====> 자체 회원가입 실패");
				resultMap.put("message", "회원가입에 실패하였습니다.");
				status = HttpStatus.NOT_FOUND;
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("자체 회원가입 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "NickName Overlap Check", notes = "닉네임 중복 체크")
	@GetMapping("/check/{nick}")
	public ResponseEntity<Map<String, Object>> allList(@PathVariable String nick) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 닉네임 중복 체크");

			int result = userservice.check_nick(nick);
			boolean flag = false;

			if (result >= 1) {
				resultMap.put("message", "사용중인 닉네임입니다.");
				resultMap.put("flag", flag);
			} else {
				flag = true;
				resultMap.put("message", "사용가능한 닉네임입니다.");
				resultMap.put("flag", flag);
			}
			status = HttpStatus.ACCEPTED;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("닉네임 중복 체크 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 로그인을 시킬때
	// 1. 일단 넣어 그리고 uid에 인증번호를 넣고 u_flag를 1로 해줘 그럼 인증번호만 확인한 애야,
	// 2. 인증번호 확인을 누르고 맞는지 확인 해주 그리고
	// 3. 회원가입을 누르면 전체 정보 입력되고 u_flag는 0으로 바꿔줘

	@ApiOperation(value = "Auth Mail Send", notes = "인증번호 메일 보내기")
	@PostMapping("/sendMail")
	public ResponseEntity<Map<String, Object>> sendMail(@RequestParam UserDto user, @RequestParam String auth) {
		// 1. 이미 있는 메일이야? 메일 중복 확인

		// 2. 메일 중복 아니야? 난수 생성해서 uid세팅하고, u_flag는 1로 해서 비활성화! mail 세팅하고!

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			String mail = user.getU_email();

			logger.info("=====> 이메일 중복 체크");

			UserDto checkEmail = userservice.checkEmail(mail);
			if (checkEmail == null) { // 메일 중복 확인끝! 중복된 메일이 없으시네요~~
				// 이제 메일을 보내봅시다
				MailUtils sendMail = new MailUtils(mailSender);
				sendMail.setSubject("[WALKIE DOGGIE] 워키도키 회원가입 이메일 인증");
				sendMail.setText(new StringBuffer().append("<h1>[워키도키 회원가입 이메일 인증]</h1>")
						.append("<h3> 안녕하세요? 워키도키입니다 </h3>").append("<h4>아래 인증번호를 입력하여 인증 확인 해주시기 바랍니다.</h4>")
						.append("<p>인증번호 : " + auth + "</p>").toString());
				sendMail.setFrom("ssafyuser@gmail.com", "WALKIE DOGGIE 관리자");
				sendMail.setTo(mail);
				sendMail.send();
				logger.info("=====> 메일보냈어요");
				// uid, mail, flag 세팅하러갑시다!
				resultMap.put("message", "인증 메일을 보냈습니다. 확인해주십시오");
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", "회원가입된 메일입니다.");
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("메일 중복 체크 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// mypage 마이페이지
	@ApiOperation(value = "Mypage Info", notes = "마이페이지")
	@GetMapping("/info")//user/info
	public ResponseEntity<Map<String, Object>> myInfo(HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		String jwt = req.getHeader("doggie_token");

		try {
			logger.info("=====> 마이페이지");
			
			resultMap.putAll(jwtutil.get(req.getHeader("doggie_token")));
			String uid = jwtutil.getUserId(jwt);
			System.out.println("uid : "+uid);
			
			List<PetDto> list = userservice.petInfo(uid);
			resultMap.put("petList", list);
			resultMap.put("message", "SUCCESS");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("메일 중복 체크 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	// 지역등록
	@ApiOperation(value = "Set Address", notes = "지역등록")
	@GetMapping("/address")//user/address
	public ResponseEntity<Map<String, Object>> setAddress(@RequestParam String uid,@RequestParam String add) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 주소설정하기");
			int result = userservice.setAddress(uid, add);
			
			if(result>=1) {
				resultMap.put("message", "주소 등록이 완료하였습니다");
				resultMap.put("flag", "SUCCESS");
			}else {
				resultMap.put("message", "주소 등록에 실패하였습니다");
				resultMap.put("flag", "FAIL");

			}
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("메일 중복 체크 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	// 비밀번호 확인
	@ApiOperation(value = "Check Password", notes = "비밀번호 확인")
	@PostMapping("/check/pass")//user/address
	public ResponseEntity<Map<String, Object>> CheckPass(@RequestBody UserDto user) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 비밀번호 맞는지 확인하기");
			boolean result = checkPass(user);
			resultMap.put("message", result);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("메일 중복 체크 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	public boolean checkPass(UserDto user) {
		UserDto check = userservice.checkPass(user);
		boolean flag = false;
		if(check!=null) {
			flag = true;
		}
		return flag;
	}
	
	// 비밀번호 변경
	@ApiOperation(value = "Change Password", notes = "비밀번호 변경")
	@PutMapping("/change/pass")//user/address
	public ResponseEntity<Map<String, Object>> changePass(@RequestParam UserDto user,@RequestParam String newPass) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 비밀번호 변경하기");
			boolean result = checkPass(user);
			if(result==true) {
				//비밀번호 변경가능
				int res = userservice.changePass(user.getUid(), newPass);
				if(res>=1) {
					resultMap.put("message", "비밀번호 변경에 성공하였습니다.");
				}else {
					resultMap.put("message", "비밀번호 변경에 실패하였습니다.");
				}
			}else {
				//비밀번호가 맞지 않아서 변경 불가
				resultMap.put("message", "비밀번호가 맞지않습니다");

			}
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("비밀번호 변경 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	//계정삭제
	@ApiOperation(value = "Leave User", notes = "회원탈퇴")
	@PutMapping("/leave")
	public ResponseEntity<Map<String, Object>> leaveUser(@RequestBody UserDto user){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 회원탈퇴");
			
			int result = userservice.leaveUser(user.getUid(), user.getU_password());
			boolean flag = false;
			if(result>=1) {
				flag = true;
				resultMap.put("message", "탈퇴되었습니다.");
				resultMap.put("flag", flag);
			}else {
				resultMap.put("message", "탈퇴 실패 하였습니다. 비밀번호를 확인해주세요 ");
				resultMap.put("flag", flag);
			}
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("메일 중복 체크 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
