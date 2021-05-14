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
import com.ssafy.pet.service.DataService;
import com.ssafy.pet.service.WalkService;
import com.ssafy.pet.util.S3Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("DataController V1")
@RestController
@RequestMapping("/data")
public class DataController {
	
	public static final Logger logger = LoggerFactory.getLogger(DataController.class);

	@Autowired
    private DataService dataService;
	
	/*
     * 기능: 통계 정보
     * 
     * developer: 윤수민
     * 
     * @param : lidList<lid>
     * 
     * @return : message,
     * likeList(lid, p_latitude, p_longtitude, l_image, l_desc, l_date, pe_name) 
     */
	@ApiOperation(value = "statistics", notes = "통계 정보")
    @GetMapping("/statistics/{peid}")
    public ResponseEntity<Map<String, Object>> getStatistics(@PathVariable("peid") String peid) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
			logger.info("data/statistics 호출성공");

			String location = dataService.getLocation(peid);

			if(location != null){
				// 산책 횟수
				int l_walk_count = dataService.getLWalkCount(location); 
				int p_walk_count = dataService.getPWalkCount(peid);

				// 총 산책 시간
				int l_total_time;
				int p_total_time;

				// 산책 시간대
				int l_walk_time;
				int p_walk_time;

			}else{
				logger.info("=====> 지역정보 없음");
                resultMap.put("message", "지역정보가 없습니다.");
				status = HttpStatus.NOT_FOUND;
            }
			}



			resultMap.put("location", location);
			status = HttpStatus.ACCEPTED;
			// List<Map<String, Object>> likeList = new ArrayList<Map<String, Object>>();
			// for (Integer lid : lidList) {
			// 	Map<String, Object> likeMap = walkService.getLikeInfo(lid);
			// 	if(likeMap != null){
			// 		likeList.add(likeMap);
			// 	}
			// }

			// logger.info("=====> 산책중 좋아요 리스트 호출 성공");
			// resultMap.put("likeList", likeList);
			// resultMap.put("message", "산책중 좋아요 리스트 호출에 성공하였습니다.");
			// status = HttpStatus.ACCEPTED;
            
        } catch (Exception e) {
            logger.error("통계 정보 호출 실패 : {}", e);
			resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}
