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
import com.ssafy.pet.dto.PlaceDto;
import com.ssafy.pet.dto.WalkDto;
import com.ssafy.pet.service.WalkService;
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

	@Autowired
    private WalkService walkService;
	
	/*
     * 기능: 산책 정보 저장
     * 
     * developer: 윤수민
     * 
     * @param WalkDto
     * 
     * @return message, wid
     */
    @ApiOperation(value = "Walk Info Insert", notes = "산책 시작 시 산책 정보 저장")
    @PostMapping("/insert")
    public ResponseEntity<Map<String, Object>> insert_walk(@RequestBody WalkDto walkDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
			logger.info("=====> 산책 정보 저장 시작");
            
			int result = walkService.createWalk(walkDto);

			if(result == 1){
				logger.info("=====> 산책 정보 저장 성공");
				resultMap.put("message", "산책 정보 저장에 성공하였습니다.");
				resultMap.put("wid", walkDto.getWid());
				status = HttpStatus.ACCEPTED;
			}else {
				logger.info("=====> 산책 정보 저장 실패");
				resultMap.put("message", "산책 정보 저장에 실패하였습니다.");
				status = HttpStatus.NOT_FOUND;
			}

        } catch (Exception e) {
            logger.error("산책 정보 저장 실패 : {}", e);
			resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

	/*
     * 기능: 산책 경로 저장
     * 
     * developer: 윤수민
     * 
     * @param file, wid
     * 
     * @return message
     */
	@ApiOperation(value = "Walk Route Insert", notes = "산책 경로 저장")
	@PutMapping("/routeInsert")
	public ResponseEntity<Map<String, Object>> save_route(@RequestParam(value = "wid") int wid,
	@RequestParam(value = "file") MultipartFile file) {
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
			
			Map<String, Object> map = new HashMap<>();
			map.put("url", url);
			map.put("wid", wid);
			int result = walkService.saveRoute(map);

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

	/*
     * 기능: 산책 중 특정 장소 클릭 시 좋아요/ 싫어요
     * 
     * developer: 윤수민
     * 
     * @param p_latitude, p_longtitude, pe_id, w_id, isLike
     * 
     * @return message
     */
    @ApiOperation(value = "Like/unlike place", notes = "산책 중 특정 장소 클릭 시 좋아요/싫어요")
    @PostMapping("/clickPlace")
    public ResponseEntity<Map<String, Object>> like_place(@RequestBody Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
			logger.info("=====> 장소 클릭 시작");
            
			// 등록된 장소인지 먼저 확인
			int pCount = walkService.checkPlace(param);

			if(pCount == 0){
				// 등록되지 않은 장소인 경우이므로 PlaceDTO 생성
				
				// 싫어요/좋아요 누른 경우에 따라 PlaceDTO 카운트 초기 설정
				if((int)param.get("isLike") == 0){
					param.put("p_like", 0);
					param.put("p_hate", 1);
				}else{
					param.put("p_like", 1);
					param.put("p_hate", 0);
				}

				PlaceDto placeDto = walkService.createPlace(param);

				if(placeDto != null){
					logger.info("=====> 장소 생성 성공");
					param.put("pid", placeDto.getPid());
					
					int result = walkService.clickPlace(param);

					if (result == 1) {
						logger.info("=====> 좋아요/싫어요 성공");
						resultMap.put("message", "좋아요/싫어요 성공하였습니다.");
						status = HttpStatus.ACCEPTED;
					} else {
						logger.info("=====> 좋아요/싫어요 실패");
						resultMap.put("message", "좋아요/싫어요 실패하였습니다.");
						status = HttpStatus.NOT_FOUND;
					}

				}else{
					logger.info("=====> 장소 생성 실패");
					resultMap.put("message", "산책 종료에 실패하였습니다.");
				status = HttpStatus.NOT_FOUND;
				}
			}else{
				// 등록되어 있는 장소이므로 좋아요/싫어요 카운트 처리
				int pid = walkService.getPid(param);
				param.put("pid", pid);

				int result = walkService.clickPlace(param);

				if (result == 1) {
					logger.info("=====> 좋아요/싫어요 성공");
					
					// likeplace에서만 처리된 상태, place 테이블의 카운트 처리
					if((int)param.get("isLike") == 0){
						int result2 = walkService.minusPlace(pid);

						if (result2 == 1) {
							logger.info("=====> 좋아요/싫어요 카운트 처리 성공");
							resultMap.put("message", "좋아요/싫어요 카운트 처리 성공하였습니다.");
							status = HttpStatus.ACCEPTED;
						} else {
							logger.info("=====> 좋아요/싫어요 실패");
							resultMap.put("message", "좋아요/싫어요 실패하였습니다.");
							status = HttpStatus.NOT_FOUND;
						}

					}else{
						int result2 = walkService.plusPlace(pid);

						if (result2 == 1) {
							logger.info("=====> 좋아요/싫어요 카운트 처리 성공");
							resultMap.put("message", "좋아요/싫어요 카운트 처리 성공하였습니다.");
							status = HttpStatus.ACCEPTED;
						} else {
							logger.info("=====> 좋아요/싫어요 카운트 처리 실패");
							resultMap.put("message", "좋아요/싫어요 카운트 처리 실패하였습니다.");
							status = HttpStatus.NOT_FOUND;
						}

					}

				} else {
					logger.info("=====> 좋아요/싫어요 실패");
					resultMap.put("message", "좋아요/싫어요 실패하였습니다.");
					status = HttpStatus.NOT_FOUND;
				}
			}

        } catch (Exception e) {
            logger.error("좋아요/싫어요 처리 실패 : {}", e);
			resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}
