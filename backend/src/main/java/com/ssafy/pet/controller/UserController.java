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
import com.ssafy.pet.dto.EmailAuthDto;
import com.ssafy.pet.dto.PetDto;
import com.ssafy.pet.dto.UserDto;
import com.ssafy.pet.service.UserService;
import com.ssafy.pet.util.JWTUtil;
import com.ssafy.pet.util.MailUtils;
import com.ssafy.pet.util.S3Util;
import com.ssafy.pet.util.SecurityUtil;
import com.ssafy.pet.util.UidUtil;

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
	
	@Autowired
	private SecurityUtil securityutil;
	
	@Autowired
	private UidUtil uidutil;
	//컨트롤러를 찾아서

	// 회원가입하기
	@ApiOperation(value = "User Signup", notes = "자체로그인 회원가입")
	@PostMapping("/confirm/signup")
	public ResponseEntity<Map<String, Object>> signup(@RequestBody UserDto user) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 자체 회원가입 시작");
			//이메일 인증이 끝난애인지 확인해보쟈! 
			EmailAuthDto authdto = userservice.checkEmailAuth(user.getU_email());
			
			//인증메일을 보낸 친구인지 인증이 처리된 친구인지
			if(authdto!=null && authdto.getFlag()==1) { 
				user.setUid(uidutil.MakeUid());
				
				String pass = securityutil.bytesToHex(securityutil.sha256(user.getU_password()));
				user.setU_password(pass);
				
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
			}else { //null일때는 인증메일을 보내지도 않은 친구 flag=0일땐 인증을 확인하지않은 친구
				resultMap.put("message", "이메일 인증 확인해주십시오.");
				status = HttpStatus.ACCEPTED;	
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
	@GetMapping("/confirm/check/{nick}")
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

	//1. 인증 메일 보내기  (랜덤값을 만들어서 sha-256방식으로 암호화 했으면 좋겠어)
	@ApiOperation(value = "Auth Mail Send", notes = "인증번호 메일 보내기")
	@PostMapping("/confirm/sendMail")
	public ResponseEntity<Map<String, Object>> sendMail(@RequestBody EmailAuthDto user) {
		// 1. 이미 있는 메일이야? 메일 중복 확인

		// 2. 메일 중복 아니야? 난수 생성해서 uid세팅하고, u_flag는 1로 해서 비활성화! mail 세팅하고!

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			System.out.println("hiru : "+user);
			String mail = user.getEmail();

			logger.info("=====> 이메일 중복 체크");

			UserDto checkEmail = userservice.checkEmail(mail);
			
			if (checkEmail == null) { // 메일 중복 확인끝! 중복된 메일이 없으시네요~~ 메일로 등록된 유저가 없으면 인증메일은 몇번이고 받아도되니까
				//메일인증 테이블에 있는지 확인해보쟈!
				EmailAuthDto auth = userservice.checkAuth(mail);//이거왜한거야? 궁금하네 망할  아  밑에 있꾸나
				//인증메일은 받은게 처음인지 여러번 누른애인지 확인하는거
				
				String code = uidutil.MakeUid();
				System.out.println("메일 코드 : "+code);
				
				// 이제 메일을 보내봅시다
				MailUtils sendMail = new MailUtils(mailSender);
				sendMail.setSubject("[WALKIE DOGGIE] 워키도키 회원가입 이메일 인증");
				sendMail.setText(new StringBuffer().append("<h1>[워키도키 회원가입 이메일 인증]</h1>")
						.append("<h3> 안녕하세요? 워키도키입니다 </h3>").append("<h4>아래 인증번호를 입력하여 인증 확인 해주시기 바랍니다.</h4>")
						.append("<p>인증번호 : " + code + "</p>").toString());
				sendMail.setFrom("ssafyuser@gmail.com", "WALKIE DOGGIE 관리자");
				sendMail.setTo(mail);
				sendMail.send();
				logger.info("=====> 메일보냈어요");
				
				EmailAuthDto temp = new EmailAuthDto();
				temp.setEmail(mail);
				temp.setAuth(securityutil.bytesToHex(securityutil.sha256(code)));
				int res = 0;
				if(auth==null) {
					//인증메일 한번만 보낸놈
					res = userservice.insertAuth(temp);
				}else {
					//인증메일 한번이 아닌놈
					res = userservice.updateAuth(temp);
				}
				
				if(res==1) {
					resultMap.put("message", "인증 메일을 보냈습니다. 확인해주십시오");
				}
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
	
	//2. 인증 번호 확인 하기
	@ApiOperation(value = "Auth Mail Check", notes = "인증번호 확인")
	@PostMapping("/confirm/checkMail")
	public ResponseEntity<Map<String, Object>> checkMail(@RequestBody EmailAuthDto user) {
		// 1. 이미 있는 메일이야? 메일 중복 확인

		// 2. 메일 중복 아니야? 난수 생성해서 uid세팅하고, u_flag는 1로 해서 비활성화! mail 세팅하고!

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 중복처리해서 인증번호맡는지 확인하쟈");
			

			EmailAuthDto check= userservice.checkAuth(user.getEmail());
			
			String temp = securityutil.bytesToHex(securityutil.sha256(user.getAuth()));
			
			if(temp.equals(check.getAuth())) {
				resultMap.put("message", "인증 확인이 되었습니다.");
				userservice.updateFlag(user.getEmail());

			}else {
				resultMap.put("message", "인증 번호가 틀렸습니다.");
				
			}
				status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("인증 번호 체크 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	

	// mypage 마이페이지
	@ApiOperation(value = "Mypage Info", notes = "마이페이지")
	@GetMapping("/info")//user/info
	public ResponseEntity<Map<String, Object>> myInfo(@RequestParam String uid) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
//		String jwt = req.getHeader("doggie_token");

		try {
			logger.info("=====> 마이페이지");
			
//			resultMap.putAll(jwtutil.get(req.getHeader("doggie_token")));
//			String juid = jwtutil.getUserId(jwt);
//			System.out.println("uid : "+uid);
			
			List<PetDto> list = userservice.petInfo(uid);
			resultMap.put("petList", list);
			resultMap.put("message", "SUCCESS");
			status = HttpStatus.ACCEPTED;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("마이페이지 실패 : {}", e);
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
			logger.error("지역 등록 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	// 비밀번호 확인
	//uid password 필수
	@ApiOperation(value = "Check Password", notes = "비밀번호 확인")
	@PostMapping("/check/pass")//user/address
	public ResponseEntity<Map<String, Object>> CheckPass(@RequestBody UserDto user) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 비밀번호 맞는지 확인하기");
			String pass = securityutil.bytesToHex(securityutil.sha256(user.getU_password()));
			user.setU_password(pass);
			System.out.println(user.getU_password());
			
			UserDto check = userservice.checkPass(user.getUid()); 
			if(check.getU_password().equals(pass)) {
				resultMap.put("message", true);
			}else {
				resultMap.put("message", false);	
			}
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("비밀번호 체크 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	// 비밀번호 변경
	//uid, password 필수
	@ApiOperation(value = "Change Password", notes = "비밀번호 변경")
	@PutMapping("/confirm/change/pass")//user/address
	public ResponseEntity<Map<String, Object>> changePass(@RequestBody Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 비밀번호 변경하기");
			
			String pass = param.get("u_password").toString();
			String newOne = param.get("newPass").toString();
			String uid = param.get("uid").toString();
			
			pass = securityutil.bytesToHex(securityutil.sha256(pass));
			newOne = securityutil.bytesToHex(securityutil.sha256(newOne));
			
			UserDto check = userservice.checkPass(uid);
			
			if(check.getU_password().equals(pass)) {
				//비밀번호 변경가능
				int res = userservice.changePass(uid, newOne);
				if(res>=1) {
					resultMap.put("message", "비밀번호 변경에 성공하였습니다.");
				}else {
					resultMap.put("message", "비밀번호 변경에 실패하였습니다.");
				}				
			}else {
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
			String pass = securityutil.bytesToHex(securityutil.sha256(user.getU_password()));
			user.setU_password(pass);
			
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
			logger.error("회원 탈퇴 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
