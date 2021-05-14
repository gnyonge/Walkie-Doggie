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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.pet.dto.DiaryDto;
import com.ssafy.pet.dto.HealthDto;
import com.ssafy.pet.service.DiaryService;
import com.ssafy.pet.service.HealthService;
import com.ssafy.pet.util.S3Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("DiaryController V1")
@RestController
@RequestMapping(value = "/diary")
public class DiaryController {

	public static final Logger logger = LoggerFactory.getLogger(DiaryController.class);

	@Autowired
	DiaryService dservice;

	@Autowired
	HealthService hservice;

	@Autowired
	private S3Util s3util;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	// 기록일지 등록
	@ApiOperation(value = "Diary Insert", notes = "기록일지 등록")
	@PostMapping("/insert")
	public ResponseEntity<Map<String, Object>> insert_diary(@RequestPart MultipartFile file,
			@RequestPart DiaryDto diary, @RequestPart List<HealthDto> health_list) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 기록일지 등록 시작!");
			String originName = file.getOriginalFilename(); // 파일 이름 가져오기

			String ext = originName.substring(originName.lastIndexOf('.')); // 파일 확장명 가져오기
			String saveFileName = UUID.randomUUID().toString() + ext; // 암호화해서 파일확장넣어주기
			String path = System.getProperty("user.dir"); // 경로설정해주고

			File tempfile = new File(path, saveFileName); // 경로에 파일만들어주고

			String line = "diary/";

			saveFileName = line + saveFileName;

			file.transferTo(tempfile);
			s3util.setS3Client().putObject(new PutObjectRequest(bucket, saveFileName, tempfile)
					.withCannedAcl(CannedAccessControlList.PublicRead));
			String url = s3util.setS3Client().getUrl(bucket, saveFileName).toString();
			tempfile.delete();

			diary.setD_img(url);

			if (health_list != null) {
				for (HealthDto dto : health_list) {
					hservice.insert_health(dto);
				}
			}

			String peid = diary.getPeid();
			String h_date = diary.getD_date();

			List<HealthDto> new_health_list = hservice.get_health(peid, h_date);

			int result = dservice.insert_diary(diary);
			
			Map<String, Object> map = new HashMap<>();
			map.put("d_date", h_date);
			map.put("peid", peid);
			List<Map<String, Object>> walk_list = dservice.get_walk(map);
			
			DiaryDto new_diary = dservice.get_diary(peid, h_date);

			if (result == 1) {
				logger.info("=====> 기록일지 등록 성공");
				resultMap.put("Diary", new_diary);
				resultMap.put("Health_list", new_health_list);
				resultMap.put("Walk_list", walk_list);
				resultMap.put("message", "기록일지 등록에 성공하였습니다.");
				status = HttpStatus.ACCEPTED;
			} else {
				logger.info("=====> 기록일지 등록 실패");
				resultMap.put("message", "기록이리 등록에 실패하였습니다.");
				status = HttpStatus.NOT_FOUND;
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("기록일지 등록 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 기록일지 사진없이 등록하기
	@ApiOperation(value = "Diary Insert (NO FILE)", notes = "기록일지 등록 사진없이")
	@PostMapping("/insert/np")
	public ResponseEntity<Map<String, Object>> insert_diary_nopic(@RequestPart DiaryDto diary,
			@RequestPart List<HealthDto> health_list) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 기록일지 등록 시작! 사진없이!");
			System.out.println(diary);

			if (health_list != null) {
				for (HealthDto dto : health_list) {
					hservice.insert_health(dto);
				}
			}

			String peid = diary.getPeid();
			String h_date = diary.getD_date();

			List<HealthDto> new_health_list = hservice.get_health(peid, h_date);

			int result = dservice.insert_diary(diary);
			
			Map<String, Object> map = new HashMap<>();
			map.put("d_date", h_date);
			map.put("peid", peid);
			List<Map<String, Object>> walk_list = dservice.get_walk(map);
			
			DiaryDto new_diary = dservice.get_diary(peid, h_date);

			if (result == 1) {
				logger.info("=====> 기록일지 등록 성공");
				resultMap.put("Diary", new_diary);
				resultMap.put("Health_list", new_health_list);
				resultMap.put("Walk_list", walk_list);
				resultMap.put("message", "기록일지 등록에 성공하였습니다.");
				status = HttpStatus.ACCEPTED;
			} else {
				logger.info("=====> 기록일지 등록 실패");
				resultMap.put("message", "기록일지 등록에 실패하였습니다.");
				status = HttpStatus.NOT_FOUND;
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("기록일지 등록 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 기록일지 조회하기
	@ApiOperation(value = "Diary Show", notes = "기록일지 조회")
	@GetMapping("/{d_date}")
	public ResponseEntity<Map<String, Object>> get_diary(@PathVariable String d_date, @RequestParam String peid) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 기록일지 조회 시작!");
			DiaryDto diary = dservice.get_diary(peid, d_date);

			logger.info("=====> 건강 조회 시작!");
			List<HealthDto> health_list = hservice.get_health(peid, d_date);

			Map<String, Object> map = new HashMap<>();
			map.put("d_date", d_date);
			map.put("peid", peid);
			List<Map<String, Object>> walk_list = dservice.get_walk(map);

			resultMap.put("Diary", diary); // 기록일지
			resultMap.put("Health_list", health_list); // 건강 목록
			resultMap.put("Walk_list", walk_list); // 산책 정보 목록
			resultMap.put("message", "기록일지 가져오기 성공하였습니다.");
			status = HttpStatus.ACCEPTED;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("기록일지 조회 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 기록일지 수정하기
	@ApiOperation(value = "Diary Update", notes = "기록일지 수정")
	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> update_diary(@RequestPart MultipartFile file,
			@RequestPart DiaryDto diary, @RequestPart List<HealthDto> health_list) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 기록일지 수정 시작!");
			int result = dservice.update_diary(diary);

			String del_img = diary.getD_img();

			if (del_img != null) {
				s3util.setS3Client().deleteObject(new DeleteObjectRequest(bucket, del_img));
			}

			// file로 사진등록
			String originName = file.getOriginalFilename();

			String ext = originName.substring(originName.lastIndexOf('.'));
			String saveFileName = UUID.randomUUID().toString() + ext;
			String path = System.getProperty("user.dir");

			File tempfile = new File(path, saveFileName);

			String line = "diary/";

			saveFileName = line + saveFileName;

			file.transferTo(tempfile);
			s3util.setS3Client().putObject(new PutObjectRequest(bucket, saveFileName, tempfile)
					.withCannedAcl(CannedAccessControlList.PublicRead));
			String url = s3util.setS3Client().getUrl(bucket, saveFileName).toString();
			tempfile.delete();

			diary.setD_img(url);

			String peid = diary.getPeid();
			String h_date = diary.getD_date();

			List<HealthDto> delete_health_list = hservice.get_health(peid, h_date);

			for (HealthDto delete_dto : delete_health_list) {
				hservice.delete_health(delete_dto);
			}

			if (health_list != null) {
				for (HealthDto dto : health_list) {
					hservice.insert_health(dto);
				}
			}

			List<HealthDto> new_health_list = hservice.get_health(peid, h_date);
			
			Map<String, Object> map = new HashMap<>();
			map.put("d_date", h_date);
			map.put("peid", peid);
			List<Map<String, Object>> walk_list = dservice.get_walk(map);

			int update = dservice.update_pic(diary);

			if (update == 1) {
				logger.info("=====> 기록일지 수정 성공");
				resultMap.put("Diary", diary);
				resultMap.put("Health_list", new_health_list);
				resultMap.put("Walk_list", walk_list);
				resultMap.put("message", "기록일지 수정에 성공하였습니다.");
				status = HttpStatus.ACCEPTED;
			} else {
				logger.info("=====> 기록일지 수정 실패");
				resultMap.put("message", "기록일지 수정에 실패하였습니다.");
				status = HttpStatus.NOT_FOUND;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("기록일지 수정 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 기록일지 사진없이 수정하기
	@ApiOperation(value = "Diary Update (NO FILE)", notes = "기록일지 수정 사진없이")
	@PutMapping("/update/np")
	public ResponseEntity<Map<String, Object>> update_diary_nopic(@RequestPart DiaryDto diary,
			@RequestPart List<HealthDto> health_list) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 기록일지 수정 시작!");

			String peid = diary.getPeid();
			String h_date = diary.getD_date();

			List<HealthDto> delete_health_list = hservice.get_health(peid, h_date);

			for (HealthDto delete_dto : delete_health_list) {
				hservice.delete_health(delete_dto);
			}

			if (health_list != null) {
				for (HealthDto dto : health_list) {
					hservice.insert_health(dto);
				}
			}

			List<HealthDto> new_health_list = hservice.get_health(peid, h_date);
			
			Map<String, Object> map = new HashMap<>();
			map.put("d_date", h_date);
			map.put("peid", peid);
			List<Map<String, Object>> walk_list = dservice.get_walk(map);

			int result = dservice.update_diary(diary);

			if (result == 1) {
				logger.info("=====> 기록일지 수정 성공");
				resultMap.put("Diary", diary);
				resultMap.put("Health_list", new_health_list);
				resultMap.put("Walk_list", walk_list);
				resultMap.put("message", "기록일지 수정에 성공하였습니다.");
				status = HttpStatus.ACCEPTED;
			} else {
				logger.info("=====> 기록일지 수정 실패");
				resultMap.put("message", "기록일지 수정에 실패하였습니다.");
				status = HttpStatus.NOT_FOUND;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("기록일지 수정 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 기록일지 삭제하기
	@ApiOperation(value = "Diary Delete", notes = "기록일지 삭제")
	@PutMapping("/delete")
	public ResponseEntity<Map<String, Object>> delete_diary(@RequestPart DiaryDto diary,
			@RequestPart List<HealthDto> health_list) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 기록일지 삭제 시작!");

			if (health_list != null) {
				for (HealthDto dto : health_list) {
					hservice.delete_health(dto);
				}
			}

			int delete = dservice.delete_diary(diary);
			if (delete == 1) {
				resultMap.put("message", "기록일지를 성공적으로 삭제하였습니다.");
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", "기록일지 삭제에 실패하였습니다.");
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("기록일지 삭제 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 기록일지 전체 조회
	@ApiOperation(value = "All Diary Show", notes = "기록일지 전체 조회")
	@GetMapping("/show")
	public ResponseEntity<Map<String, Object>> get_all_diary(@RequestParam String peid) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 기록일지 전체 조회 시작!");
			List<String> all_diary = dservice.get_all_diary(peid);

			resultMap.put("All_diary", all_diary);
			resultMap.put("message", "기록일지 가져오기 성공하였습니다.");
			status = HttpStatus.ACCEPTED;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("기록일지 조회 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	// 기록일지 전체 조회
	@ApiOperation(value = "Walk Show", notes = "기록일지 산책 조회")
	@GetMapping("/{d_date}/walk")
	public ResponseEntity<Map<String, Object>> get_walk(@PathVariable String d_date, @RequestParam String peid) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 기록일지 산책 조회 시작!");
			
			Map<String, Object> map = new HashMap<>();
			map.put("d_date", d_date);
			map.put("peid", peid);
			List<Map<String, Object>> walk_list = dservice.get_walk(map);

			resultMap.put("Walk_list", walk_list);
			resultMap.put("message", "기록일지 산책 가져오기 성공하였습니다.");
			status = HttpStatus.ACCEPTED;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("기록일지 산책 조회 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
