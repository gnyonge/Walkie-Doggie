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
import com.ssafy.pet.dto.AllergyDto;
import com.ssafy.pet.dto.HealthDto;
import com.ssafy.pet.dto.PetDto;
import com.ssafy.pet.service.PetService;
import com.ssafy.pet.util.S3Util;
import com.ssafy.pet.util.UidUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("PetController V1")
@RestController
@RequestMapping("/pet")
public class PetController {

	public static final Logger logger = LoggerFactory.getLogger(PetController.class);

	@Autowired
	private PetService petservice;

	@Autowired
	private S3Util s3util;

	@Autowired
	private UidUtil uidutil;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@ApiOperation(value = "Check Pet Regist", notes = "반려견 등록 가능 여부 확인")
	@GetMapping("/check/add")
	public ResponseEntity<Map<String, Object>> posiblePet(@RequestParam String uid) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 주소 등록 여부 ");
			String result = petservice.check_add(uid);
			boolean flag = false;
			if (result == null) {
				resultMap.put("message", "주소 등록 후 반려견등록이 가능합니다.");
				resultMap.put("flag", flag);
			} else {
				flag = true;
				resultMap.put("flag", flag);

			}
			status = HttpStatus.ACCEPTED;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("반려견 등록 가능 체크 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 반려견 등록
	@ApiOperation(value = "Pet Regist", notes = "반려견 등록")
	@PostMapping("/insert")
	public ResponseEntity<Map<String, Object>> regist_pet(@RequestPart MultipartFile file, @RequestPart PetDto pet,
			@RequestPart List<AllergyDto> allergy) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 반려견 등록 시작");

			String peid = uidutil.MakeUid();

			pet.setPeid(peid);// 반려견 peid 설정

			if (file != null) {
				String originName = file.getOriginalFilename(); // 파일 이름 가져오기

				String ext = originName.substring(originName.lastIndexOf('.')); // 파일 확장명 가져오기
				String saveFileName = UUID.randomUUID().toString() + ext; // 암호화해서 파일확장넣어주기
				String path = System.getProperty("user.dir"); // 경로설정해주고

				File tempfile = new File(path, saveFileName); // 경로에 파일만들어주고

				String line = "pet/";

				saveFileName = line + saveFileName;

				file.transferTo(tempfile);
				s3util.setS3Client().putObject(new PutObjectRequest(bucket, saveFileName, tempfile)
						.withCannedAcl(CannedAccessControlList.PublicRead));
				String url = s3util.setS3Client().getUrl(bucket, saveFileName).toString();
				pet.setPe_profile_photo(url);
				tempfile.delete();
			} else {
				pet.setPe_profile_photo(null);
			}

			int result = petservice.regist_pet(pet);

			if (allergy != null) {
				for (AllergyDto all : allergy) {
					all.setPeid(peid);
					petservice.insert_allergy(all);
				}
			}

			List<AllergyDto> new_allergy_list = petservice.show_allergy(peid);

			if (result == 1) {
				logger.info("=====> 반려견 등록 성공");
				resultMap.put("pet", pet);
				resultMap.put("allergy", new_allergy_list);
				resultMap.put("message", "반려견 등록에 성공하였습니다.");
				status = HttpStatus.ACCEPTED;
			} else {
				logger.info("=====> 반려견 등록 실패");
				resultMap.put("message", "반려견 등록에 실패하였습니다.");
				status = HttpStatus.NOT_FOUND;
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("반려견 등록 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 반려견 정보 보기
	@ApiOperation(value = "Show Pet", notes = "반려견 정보 보기")
	@GetMapping("/show")
	public ResponseEntity<Map<String, Object>> showPet(@RequestParam String peid) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 반려견 정보 보기");

			PetDto pet = petservice.show_pet(peid);
			List<AllergyDto> allergy = petservice.show_allergy(peid);

			resultMap.put("pet", pet);
			resultMap.put("allergy", allergy);
			resultMap.put("message", "반려견 정보 가져오기에 성공하였습니다.");
			status = HttpStatus.ACCEPTED;

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("반려견 정보 가져오기 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	// 반려견 건강 내역 보기
	@ApiOperation(value = "Show Health", notes = "반려견 건강 등록 내역 보기")
	@GetMapping("/health/{peid}") // user/address
	public ResponseEntity<Map<String, Object>> setAddress(@PathVariable String peid) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 건강내역보기");
			List<HealthDto> listHealth = petservice.show_health(peid);

			resultMap.put("listHealth", listHealth);
			resultMap.put("message", "반려견 건강 내역 가져오기에 성공하였습니다.");
			status = HttpStatus.ACCEPTED;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("반려견 건강 등록 내역 확인 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 반려견 정보 수정
	@ApiOperation(value = "Pet Info Update", notes = "반려견 정보 수정")
	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> modify_post(@RequestPart MultipartFile file, @RequestPart PetDto pet,
			@RequestPart List<AllergyDto> allergy) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 사진, 내용 수정");
			PetDto old = petservice.show_pet(pet.getPeid());

			if (file == null) {

				if (old.getPe_profile_photo() != null) {
					s3util.setS3Client().deleteObject(new DeleteObjectRequest(bucket, old.getPe_profile_photo()));
				}

				String peid = pet.getPeid();

				List<AllergyDto> delete_allergy = petservice.show_allergy(peid);

				for (AllergyDto all : delete_allergy) {
					petservice.delete_allergy(all);
				}

				for (AllergyDto all : allergy) {
					petservice.insert_allergy(all);
				}

				List<AllergyDto> new_allergy_list = petservice.show_allergy(peid);

				int result = petservice.update_pet(pet);

				if (result == 1) {
					logger.info("=====> 반려견 정보 수정 성공");
					resultMap.put("pet", pet);
					resultMap.put("allergy", new_allergy_list);
					resultMap.put("message", "반려견 정보 수정에 성공하였습니다.");
					status = HttpStatus.ACCEPTED;
				} else {
					resultMap.put("message", "반려견 정보 수정에 실패하였습니다.");
					status = HttpStatus.ACCEPTED;
				}

			} else {
				if (old.getPe_profile_photo() != null) {
					s3util.setS3Client().deleteObject(new DeleteObjectRequest(bucket, old.getPe_profile_photo()));
				}

				String originName = file.getOriginalFilename(); // 파일 이름 가져오기

				String ext = originName.substring(originName.lastIndexOf('.')); // 파일 확장명 가져오기
				String saveFileName = UUID.randomUUID().toString() + ext; // 암호화해서 파일확장넣어주기
				String path = System.getProperty("user.dir"); // 경로설정해주고

				File tempfile = new File(path, saveFileName); // 경로에 파일만들어주고

				String line = "pet/";

				saveFileName = line + saveFileName;

				file.transferTo(tempfile);
				s3util.setS3Client().putObject(new PutObjectRequest(bucket, saveFileName, tempfile)
						.withCannedAcl(CannedAccessControlList.PublicRead));
				String url = s3util.setS3Client().getUrl(bucket, saveFileName).toString();
				tempfile.delete();

				pet.setPe_profile_photo(url);

				String peid = pet.getPeid();

				List<AllergyDto> delete_allergy = petservice.show_allergy(peid);

				for (AllergyDto all : delete_allergy) {
					petservice.delete_allergy(all);
				}

				for (AllergyDto all : allergy) {
					petservice.insert_allergy(all);
				}

				List<AllergyDto> new_allergy_list = petservice.show_allergy(peid);

				int result = petservice.update_pet(pet);

				if (result == 1) {
					logger.info("=====> 반려견 정보 수정 성공");
					resultMap.put("pet", pet);
					resultMap.put("allergy", new_allergy_list);
					resultMap.put("message", "반려견 정보 수정에 성공하였습니다.");
					status = HttpStatus.ACCEPTED;
				} else {
					resultMap.put("message", "반려견 정보 수정에 실패하였습니다.");
					status = HttpStatus.ACCEPTED;
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("반려견 정보 수정 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 계정삭제
	@ApiOperation(value = "Leave Pet", notes = "반려견 삭제")
	@PutMapping("/leave")
	public ResponseEntity<Map<String, Object>> leave_pet(@RequestPart PetDto pet,
			@RequestPart List<AllergyDto> allergy) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("=====> 반려견탈퇴");

			if (allergy != null) {
				for (AllergyDto all : allergy) {
					petservice.delete_allergy(all);
				}
			}

			int result = petservice.leave_pet(pet.getPeid());
			boolean flag = false;
			if (result >= 1) {
				flag = true;
				resultMap.put("message", "반려견 정보가 삭제되었습니다.");
				resultMap.put("flag", flag);
			} else {
				resultMap.put("message", "반려견 정보 삭제에 실패 하였습니다.");
				resultMap.put("flag", flag);
			}
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("반려견 정보 삭제 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
