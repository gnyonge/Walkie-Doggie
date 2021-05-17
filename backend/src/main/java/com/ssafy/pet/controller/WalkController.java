package com.ssafy.pet.controller;

import java.io.File;
import java.util.ArrayList;
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
     * 기능: 산책 시작
     * 
     * developer: 윤수민
     * 
     * @param WalkDto(peid, w_location)
     * 
     * @return message, wid
     */
    @ApiOperation(value = "Walk Start", notes = "산책 시작")
    @PostMapping("/start")
    public ResponseEntity<Map<String, Object>> start_walk(@RequestBody WalkDto walkDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
			logger.info("=====> 산책 시작을 시작");
            
			int result = walkService.createWalk(walkDto);

			if(result == 1){
				logger.info("=====> 산책 시작 성공");
				resultMap.put("message", "산책 시작에 성공하였습니다.");
				resultMap.put("wid", walkDto.getWid());
				logger.info("=====>wid:");
				status = HttpStatus.ACCEPTED;
			}else {
				logger.info("=====> 산책 시작 실패");
				resultMap.put("message", "산책 시작에 실패하였습니다.");
				status = HttpStatus.NOT_FOUND;
			}

        } catch (Exception e) {
            logger.error("산책 시작 실패 : {}", e);
			resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

	// /*
    //  * 기능: 산책 정보 저장
    //  * 
    //  * developer: 윤수민
    //  * 
    //  * @param WalkDto
    //  * 
    //  * @return message, wid
    //  */
    // @ApiOperation(value = "Walk Info Insert", notes = "산책 시작 시 산책 정보 저장")
    // @PostMapping("/insert")
    // public ResponseEntity<Map<String, Object>> insert_walk(@RequestBody WalkDto walkDto) {
    //     Map<String, Object> resultMap = new HashMap<>();
    //     HttpStatus status = null;
        
    //     try {
	// 		logger.info("=====> 산책 정보 저장 시작");
            
	// 		int result = walkService.createWalk(walkDto);

	// 		if(result == 1){
	// 			logger.info("=====> 산책 정보 저장 성공");
	// 			resultMap.put("message", "산책 정보 저장에 성공하였습니다.");
	// 			resultMap.put("wid", walkDto.getWid());
	// 			status = HttpStatus.ACCEPTED;
	// 		}else {
	// 			logger.info("=====> 산책 정보 저장 실패");
	// 			resultMap.put("message", "산책 정보 저장에 실패하였습니다.");
	// 			status = HttpStatus.NOT_FOUND;
	// 		}

    //     } catch (Exception e) {
    //         logger.error("산책 정보 저장 실패 : {}", e);
	// 		resultMap.put("message", e.getMessage());
    //         status = HttpStatus.INTERNAL_SERVER_ERROR;
    //     }
    //     return new ResponseEntity<Map<String, Object>>(resultMap, status);
    // }

	/*
     * 기능: 산책중 좋아요 리스트
     * 
     * developer: 윤수민
     * 
     * @param : lidList<lid>
     * 
     * @return : message,
     * likeList(lid, p_latitude, p_longtitude, l_image, l_desc, l_date, pe_name) 
     */
	@ApiOperation(value = "LikeList", notes = "산책중 좋아요 리스트")
    @PostMapping("/likeList")
    public ResponseEntity<Map<String, Object>> getLikeist(@RequestBody List<Integer> lidList) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
			logger.info("walk/likeList 호출성공");

			List<Map<String, Object>> likeList = new ArrayList<Map<String, Object>>();
			for (Integer lid : lidList) {
				Map<String, Object> likeMap = walkService.getLikeInfo(lid);
				if(likeMap != null){
					likeList.add(likeMap);
				}
			}

			logger.info("=====> 산책중 좋아요 리스트 호출 성공");
			resultMap.put("likeList", likeList);
			resultMap.put("message", "산책중 좋아요 리스트 호출에 성공하였습니다.");
			status = HttpStatus.ACCEPTED;
            
        } catch (Exception e) {
            logger.error("산책중 좋아요 리스트 호출 실패 : {}", e);
			resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}
