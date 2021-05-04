package com.ssafy.pet.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pet.dto.HealthDto;
import com.ssafy.pet.service.HealthService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("HealthController V1")
@RestController
@RequestMapping(value = "/health")
public class HealthController {

	public static final Logger logger = LoggerFactory.getLogger(HealthController.class);

	@Autowired
	HealthService hservice;

	// 건강 등록하기
	@ApiOperation(value = "Health Insert", notes = "건강 등록")
	@PostMapping("/insert")
	public ResponseEntity<Map<String, Object>> insert_nopic(@RequestBody HealthDto health) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {

			logger.info("=====> 커뮤니티 글 등록 시작! 사진없이!");
			System.out.println(health);

			int result = hservice.insert(health);

			if (result == 1) {
				logger.info("=====> 건강 등록 성공");
				resultMap.put("message", "건강 등록에 성공하였습니다.");
				status = HttpStatus.ACCEPTED;
			} else {
				logger.info("=====> 건강 등록 실패");
				resultMap.put("message", "건강 등록에 실패하였습니다.");
				status = HttpStatus.NOT_FOUND;
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("건강 등록 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
