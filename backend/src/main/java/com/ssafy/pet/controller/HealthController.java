package com.ssafy.pet.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
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
	public ResponseEntity<Map<String, Object>> insert_health(@RequestBody Map<String, Object> health_map) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 파라미터Map에서 DTO에 들어있지 않는 변수가 있어도
																					// 무시함.

		List<HealthDto> health_list = mapper.convertValue(health_map.get("health_list"),
				TypeFactory.defaultInstance().constructCollectionType(List.class, HealthDto.class));

		try {

			logger.info("=====> 건강 등록 시작!");

			for (HealthDto dto : health_list) {
				hservice.insert_health(dto);
			}

			logger.info("=====> 건강 등록 성공");
			resultMap.put("message", "건강 등록에 성공하였습니다.");
			status = HttpStatus.ACCEPTED;

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("건강 등록 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 건강 조회하기
	@ApiOperation(value = "Health Show", notes = "건강 조회")
	@GetMapping("/{h_date}")
	public ResponseEntity<Map<String, Object>> get_healt(@PathVariable String h_date, @RequestParam String peid) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 건강 조회 시작!");
			List<HealthDto> health_list = hservice.get_health(peid, h_date);

			resultMap.put("HealthList", health_list);
			resultMap.put("message", "건강 리스트 가져오기 성공하였습니다.");
			status = HttpStatus.ACCEPTED;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("건강 조회 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 건강 수정하기
	@ApiOperation(value = "Health Update", notes = "건강 수정")
	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> update_health(@RequestBody HealthDto health) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			logger.info("=====> 건강 수정 시작!");
			int result = hservice.update_health(health);

			if (result == 1) {
				logger.info("=====> 건강 수정 성공");
				resultMap.put("message", "건강 수정에 성공하였습니다.");
				status = HttpStatus.ACCEPTED;
			} else {
				logger.info("=====> 건강 수정 실패");
				resultMap.put("message", "건강 수정에 실패하였습니다.");
				status = HttpStatus.NOT_FOUND;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("건강 수정 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 건강 삭제하기
	@ApiOperation(value = "Health Delete", notes = "건강 삭제")
	@PutMapping("/delete")
	public ResponseEntity<Map<String, Object>> delete_health(@RequestBody HealthDto health) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 건강 삭제 시작!");

			int delete = hservice.delete_health(health);
			if (delete == 1) {
				logger.info("=====> 건강 삭제 성공");
				resultMap.put("message", "건강을 성공적으로 삭제하였습니다.");
				status = HttpStatus.ACCEPTED;
			} else {
				logger.info("=====> 건강 삭제 성공");
				resultMap.put("message", "건강 삭제에 실패하였습니다.");
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("건강 삭제 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
