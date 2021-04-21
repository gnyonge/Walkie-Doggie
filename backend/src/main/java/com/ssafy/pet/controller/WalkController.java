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
import com.ssafy.pet.util.S3Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("WalkController V1")
@RestController
@RequestMapping("/walk")
public class WalkController {
	
	public static final Logger logger = LoggerFactory.getLogger(WalkController.class);
	@Autowired
	private S3Util s3util;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	
	// 산책 경로 저장하기
		@ApiOperation(value = "Walk Route Insert", notes = "산책 경로 저장")
		@PostMapping("/insert")
		public ResponseEntity<Map<String, Object>> save_route(@RequestPart MultipartFile file) {
			Map<String, Object> resultMap = new HashMap<>();
			HttpStatus status = null;

			try {
				logger.info("=====> 산책 경로 저장 시작");
				String originName = file.getOriginalFilename(); // 파일 이름 가져오기

				String ext = originName.substring(originName.lastIndexOf('.')); // 파일 확장명 가져오기
				String saveFileName = UUID.randomUUID().toString() + ext; // 암호화해서 파일확장넣어주기
				String path = System.getProperty("user.dir"); // 경로설정해주고

				File tempfile = new File(path, saveFileName); // 경로에 파일만들어주고

				String line = "walk/";

				saveFileName = line + saveFileName;

				file.transferTo(tempfile);
				s3util.setS3Client().putObject(new PutObjectRequest(bucket, saveFileName, tempfile)
						.withCannedAcl(CannedAccessControlList.PublicRead));
				String url = s3util.setS3Client().getUrl(bucket, saveFileName).toString();
				tempfile.delete();
				
				int result =0;

				if (result == 1) {
					logger.info("=====> 산책 경로 저장 성공");
					resultMap.put("message", "산책 경로 저장에 성공하였습니다.");
					status = HttpStatus.ACCEPTED;
				} else {
					logger.info("=====> 산책 경로 저장 실패");
					resultMap.put("message", "산책 경로 저장에 실패하였습니다.");
					status = HttpStatus.NOT_FOUND;
				}

			} catch (Exception e) {
				// TODO: handle exception
				logger.error("산책 경로 저장 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			return new ResponseEntity<Map<String, Object>>(resultMap, status);
		}

}
