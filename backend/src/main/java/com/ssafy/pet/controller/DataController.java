package com.ssafy.pet.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pet.service.DataService;

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
				Integer l_walk_count = dataService.getLWalkCount(location); 
				if(l_walk_count == null){
					logger.info("=====> 지역별 산책 횟수 0");
					resultMap.put("l_walk_count", 0);
				}else{
					logger.info("=====> 지역별 산책 횟수 호출 성공");
					resultMap.put("l_walk_count", l_walk_count);
				}
				Integer p_walk_count = dataService.getPWalkCount(peid);
				if(p_walk_count == null){
					logger.info("=====> 내 산책 횟수 0");
					resultMap.put("p_walk_count", 0);
				}else{
					logger.info("=====> 내 산책 횟수 호출 성공");
					resultMap.put("p_walk_count", p_walk_count);
				}

				// 총 산책 시간
				Integer l_total_time = dataService.getLTotalTime(location);
				if(l_total_time == null){
					logger.info("=====> 지역별 총 산책 시간 0");
					resultMap.put("l_total_time", 0);
				}else{
					logger.info("=====> 지역별 총 산책 시간 호출 성공");
					resultMap.put("l_total_time", l_total_time);
				}
				Integer p_total_time = dataService.getPTotalTime(peid);
				if(p_total_time == null){
					logger.info("=====> 내 총 산책 시간 0");
					resultMap.put("p_total_time", 0);
				}else{
					logger.info("=====> 내 총 산책 시간 호출 성공");
					resultMap.put("p_total_time", p_total_time);					
				}

				// 산책 시간대
				Integer l_walk_time = dataService.getLWalkTime(location);
				if(l_walk_time == null){
					logger.info("=====> 지역별 산책 시간대 0");
					resultMap.put("l_walk_time", 0);
				}else{
					logger.info("=====> 지역별 산책 시간대 호출 성공");
					resultMap.put("l_walk_time", l_walk_time);
				}
				Integer p_walk_time = dataService.getPWalkTime(peid);
				if(p_walk_time == null){
					logger.info("=====> 내 산책 시간대 0");
					resultMap.put("p_walk_time", 0);
				}else{
					logger.info("=====> 내 산책 시간대 호출 성공");
					resultMap.put("p_walk_time", p_walk_time);
				}

				resultMap.put("message", "통계정보 호출 성공하였습니다.");
				status = HttpStatus.ACCEPTED;

			}else{
				logger.info("=====> 지역정보 없음");
                resultMap.put("message", "지역정보가 없습니다.");
				status = HttpStatus.NOT_FOUND;
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
