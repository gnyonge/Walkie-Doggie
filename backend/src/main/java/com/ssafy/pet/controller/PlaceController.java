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
import com.ssafy.pet.dto.LikePlaceDto;
import com.ssafy.pet.dto.PlaceDto;
import com.ssafy.pet.dto.WalkDto;
import com.ssafy.pet.service.WalkService;
import com.ssafy.pet.service.PlaceService;
import com.ssafy.pet.util.S3Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("PlaceController V1")
@RestController
@RequestMapping("/place")
public class PlaceController {
	
	public static final Logger logger = LoggerFactory.getLogger(PlaceController.class);
	@Autowired
	private S3Util s3util;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Autowired
    private PlaceService placeService;

	/*
     * 기능: 산책 중 특정 장소 좋아요 클리
     * 
     * developer: 윤수민
     * 
     * @param p_latitude, p_longtitude, peid, uid, l_image, l_desc
     * 
     * @return message
	 * 
     */
    @ApiOperation(value = "Like place", notes = "산책 중 특정 장소 좋아요 클릭")
    @PostMapping("/likePlace")
    public ResponseEntity<Map<String, Object>> likePlace(@RequestBody Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
			logger.info("place/likePlace 호출 성공");
            
			// 등록된 장소인지 먼저 확인
			Integer pid = placeService.checkPlace(param); 
			if(pid == null){ // 등록되지 않은 장소
				
				pid = placeService.createPlace(param);

				if(pid != 0){
					logger.info("=====> 장소 생성 성공");
					param.put("pid", pid);
					
					int result = placeService.clickPlace(param);

					logger.info("=====> 좋아요 성공");
					resultMap.put("message", "장소 생성 후 좋아요 성공하였습니다.");
					status = HttpStatus.ACCEPTED;
					// if (result == 1) {
					// 	logger.info("=====> 좋아요 성공");
					// 	resultMap.put("message", "장소 생성 후 좋아요 성공하였습니다.");
					// 	status = HttpStatus.ACCEPTED;
					// } else {
					// 	logger.info("=====> 좋아요 실패");
					// 	resultMap.put("message", "장소 생성 후 좋아요 실패하였습니다.");
					// 	status = HttpStatus.NOT_FOUND;
					// }

				}else{
					logger.info("=====> 장소 생성 실패");
					resultMap.put("message", "장소 생성에 실패하였습니다.");
					status = HttpStatus.NOT_FOUND;
				}
			}else{ // 기존에 등록된 장소
				logger.info("=====> 기존 등록된 장소");

				param.put("pid", pid);

				int result = placeService.checkLike(param);

				if(result == 0){ // 해당 강아지가 해당 장소 좋아요 누른 적 없는 경우
					
					int result2 = placeService.clickPlace(param);

					if (result2 == 1) {
						logger.info("=====> 좋아요 성공");

						int result3 = placeService.plusPlace(pid);

						logger.info("=====> 카운트 처리 성공");
						resultMap.put("message", "기존 등록된 장소 좋아요 카운트 처리 성공하였습니다.");
						status = HttpStatus.ACCEPTED;

						// if(result3 == 1){
						// 	logger.info("=====> 카운트 처리 성공");
						// 	resultMap.put("message", "기존 등록된 장소 좋아요 카운트 처리 성공하였습니다.");
						// 	status = HttpStatus.ACCEPTED;
						// }else{
						// 	logger.info("=====> 좋아요 실패");
						// 	resultMap.put("message", "기존 등록된 장소 좋아요 카운트 처리 실패하였습니다.");
						// 	status = HttpStatus.NOT_FOUND;
						// }

					} else {
						logger.info("=====> 좋아요 실패");
						resultMap.put("message", "기존 등록된 장소 좋아요 실패하였습니다.");
						status = HttpStatus.NOT_FOUND;
					}

				}else{
					logger.info("=====> 좋아요 중복");
						resultMap.put("message", "기존에 좋아요를 눌렀던 장소입니다.");
						status = HttpStatus.NOT_FOUND;
				}	

			}
			
        } catch (Exception e) {
            logger.error("좋아요 처리 실패 : {}", e);
			resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

	/*
     * 기능: 좋아요 포스트 수정
     * 
     * developer: 윤수민
     * 
     * @param : LikePlaceDto(lid, l_image, l_desc), login_id
     * 
     * @return : message
     */
	@ApiOperation(value = "Modify place", notes = "좋아요 누른 장소 게시글 수정")
    @PutMapping("/modify")
    public ResponseEntity<Map<String, Object>> modifyPlace(@RequestBody LikePlaceDto likePlaceDto,
            @RequestParam(value = "login_id") String login_id) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
			logger.info("place/modify 호출 성공");

            Map<String, Object> map = new HashMap<>();
            map.put("lid", likePlaceDto.getLid());
            map.put("login_id", login_id);

            if (placeService.isWriter(map) != 0) { // 로그인한 계정이 작성자가 맞는 경우
                if (placeService.modifyPlace(likePlaceDto) == 1) {
					logger.info("=====> 장소 게시글 수정 성공");
					resultMap.put("message", "장소 게시글 수정 완료하였습니다.");
					status = HttpStatus.ACCEPTED;
                }
            } else {
				logger.info("=====> 작성자가 아님");
                resultMap.put("message", "게시글 작성자만 수정할 수 있습니다.");
				status = HttpStatus.NOT_FOUND;
            }

        } catch (Exception e) {
			logger.error("좋아요 게시글 수정 실패 : {}", e);
			resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}
