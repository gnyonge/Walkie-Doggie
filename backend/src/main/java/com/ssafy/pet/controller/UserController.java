package com.ssafy.pet.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.ssafy.pet.dto.UserDto;
import com.ssafy.pet.service.UserService;
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
	
	
	// 게시물 등록하기
		@ApiOperation(value = "Community Post Insert", notes = "커뮤니티 글 등록")
		@PostMapping("/insert")
		public ResponseEntity<Map<String, Object>> insert_post(@RequestPart MultipartFile file) {
			Map<String, Object> resultMap = new HashMap<>();
			HttpStatus status = null;

			try {
				logger.info("=====> 커뮤니티 글 등록 시작");
				String originName = file.getOriginalFilename(); // 파일 이름 가져오기

				String ext = originName.substring(originName.lastIndexOf('.')); // 파일 확장명 가져오기
				String saveFileName = UUID.randomUUID().toString() + ext; // 암호화해서 파일확장넣어주기
				String path = System.getProperty("user.dir"); // 경로설정해주고

				File tempfile = new File(path, saveFileName); // 경로에 파일만들어주고

				String line = "community/";

				saveFileName = line + saveFileName;

				file.transferTo(tempfile);
				s3util.setS3Client().putObject(new PutObjectRequest(bucket, saveFileName, tempfile)
						.withCannedAcl(CannedAccessControlList.PublicRead));
				String url = s3util.setS3Client().getUrl(bucket, saveFileName).toString();
				tempfile.delete();
				
				int result =0;

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
		
		//로그인하기
		@ApiOperation(value = "User Signup", notes = "자체로그인 회원가입")
		@PostMapping("/signup")
		public ResponseEntity<Map<String, Object>> signup(@RequestBody UserDto user) {
			Map<String, Object> resultMap = new HashMap<>();
			HttpStatus status = null;

			try {
				logger.info("=====> 자체 회원가입 시작");
				//uid 만들어야해
				StringBuffer made = new StringBuffer();

				for (int i = 0; i < 6; i++) {
					char a = (char) ((Math.random() * 26) + 97); // 소문자
					int ann = (int) (Math.random() * 9) + 1; // 숫자
					made.append(a);
					made.append(ann);
				}

				char b = (char) ((Math.random() * 26) + 97);
				made.append(b);
				System.out.println(made);
				String line = made.toString();
				user.setUid(line);
				
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
				
				if(result >=1) {
					resultMap.put("message", "사용중인 닉네임입니다.");
					resultMap.put("flag", flag);
				}else {
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


}
