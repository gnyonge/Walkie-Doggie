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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.pet.dto.LikePlaceDto;
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
     * 기능: 산책 중 특정 장소 좋아요 이미지 업로드
     * 
     * developer: 윤수민
     * 
     * @param file
     * 
     * @return message, url
	 * 
     */
    @ApiOperation(value = "Like place/ Insert HotPlace image", notes = "좋아요 이미지 업로드")
    @PostMapping("/imageUpload")
    public ResponseEntity<Map<String, Object>> imageUpload( MultipartFile file) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
			logger.info("place/imageUpload 호출 성공");

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

            resultMap.put("url", url);
            resultMap.put("message", "이미지 업로드 성공하였습니다.");
			status = HttpStatus.ACCEPTED;
			
        } catch (Exception e) {
            logger.error("이미지 업로드 실패 : {}", e);
			resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

	/*
     * 기능: 산책 중 특정 장소 좋아요 클릭
     * 
     * developer: 윤수민
     * 
     * @param wid, pid, peid, l_desc, l_image, p_location
     * 
     * @return message
	 * 
     */
    @ApiOperation(value = "Like place/ Insert HotPlace post", notes = "산책 중 특정 장소 좋아요 클릭")
    @PostMapping("/likePlace")
    public ResponseEntity<Map<String, Object>> likePlace( @RequestBody Map<String, Object> param) {
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
					Integer lid = placeService.clickPlace(param);
					
					logger.info("=====> 좋아요 성공");
                    resultMap.put("lid", lid);
					resultMap.put("message", "장소 생성 후 좋아요 성공하였습니다.");
					status = HttpStatus.ACCEPTED;
				}else{
					logger.info("=====> 장소 생성 실패");
					resultMap.put("message", "장소 생성에 실패하였습니다.");
					status = HttpStatus.NOT_FOUND;
				}
			}else{ // 기존에 등록된 장소
				logger.info("=====> 기존 등록된 장소");
				param.put("pid", pid);
				int result = placeService.checkLike(param);
				if(result == 0){ // 해당 강아지가 해당 산책 시 해당 장소 좋아요 누른 적 없는 경우
					Integer lid = placeService.clickPlace(param);
					if (lid != null) {
						logger.info("=====> 좋아요 성공");
						int result3 = placeService.plusPlace(pid);
						logger.info("=====> 카운트 처리 성공");
                        resultMap.put("lid", lid);
						resultMap.put("message", "기존 등록된 장소 좋아요 카운트 처리 성공하였습니다.");
						status = HttpStatus.ACCEPTED;
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
     * 기능: 핫플레이스 포스트 수정
     * 
     * developer: 윤수민
     * 
     * @param : LikePlaceDto(lid,l_image,l_desc), peid
     * 
     * @return : message
     */
	@ApiOperation(value = "Modify HotPlace post", notes = "핫플레이스 게시글 수정")
    @PutMapping("/modify/{peid}")
    public ResponseEntity<Map<String, Object>> modifyPlace(@RequestBody LikePlaceDto likePlaceDto,
            @PathVariable("peid") String peid) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
			logger.info("place/modify 호출 성공");
            Map<String, Object> map = new HashMap<>();
            map.put("lid", likePlaceDto.getLid());
            map.put("peid", peid);
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

	/*
     * 기능: 핫플레이스 포스트 삭제
     * 
     * developer: 윤수민
     * 
     * @param : lid, peid
     * 
     * @return : message
     */
	@ApiOperation(value = "Delete HotPlace post", notes = "핫플레이스 게시글 삭제")
    @DeleteMapping("/delete/{peid}")
    public ResponseEntity<Map<String, Object>> deletePlace(@RequestParam(value = "lid") int lid,
            @PathVariable("peid") String peid) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        
        try {
			logger.info("place/delete 호출성공");
            Map<String, Object> map = new HashMap<>();
            map.put("lid", lid);
            map.put("peid", peid);
            if (placeService.isWriter(map) != 0) { // 로그인한 계정이 작성자가 맞는 경우
                if (placeService.deletePlace(lid) >= 1) {
					logger.info("=====> 장소 게시글 삭제 성공");

                    int pid = placeService.getPid(lid);
					int result = placeService.minusPlace(pid);
					if(result != 0){
						resultMap.put("message", "장소 게시글 삭제 성공 후 카운트 처리 완료하였습니다.");
						status = HttpStatus.ACCEPTED;
					}else{
						resultMap.put("message", "장소 게시글 삭제 성공 후 카운트 처리에 실패하였습니다.");
						status = HttpStatus.NOT_FOUND;
					}
					
                }
            } else {
                logger.info("=====> 작성자가 아님");
                resultMap.put("message", "게시글 작성자만 삭제할 수 있습니다.");
				status = HttpStatus.NOT_FOUND;
            }

        } catch (Exception e) {
            logger.error("좋아요 게시글 삭제 실패 : {}", e);
			resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

	/*
     * 기능: 핫플레이스 게시글 좋아요
     * 
     * developer: 윤수민
     * 
     * @param lid, uid
     * 
     * @return message
	 * 
     */
    @ApiOperation(value = "Like HotPlace post", notes = "핫플레이스 게시글 좋아요 클릭")
    @PostMapping("/likePost")
    public ResponseEntity<Map<String, Object>> likePost(@RequestParam(value = "lid") int lid, @RequestParam(value = "uid") String uid) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
			logger.info("place/likePost 호출 성공");
            Map<String, Object> param = new HashMap<>();
			param.put("lid", lid);
			param.put("uid", uid);
			// 좋아요한 게시글인지 확인
			Integer isLike = placeService.checkLikePost(param); 
			if(isLike == null){ // 좋아요하지 않은 게시글
				int result = placeService.clickLike(param);
				if(result != 0){	
					placeService.plusPost(lid);	
					logger.info("=====> 핫플레이스 게시글 좋아요 성공");
					resultMap.put("message", "핫플레이스 게시글 좋아요 성공하였습니다.");
					status = HttpStatus.ACCEPTED;
				}else{
					logger.info("=====> 핫플레이스 게시글 좋아요 실패");
					resultMap.put("message", "핫플레이스 게시글 좋아요 실패하였습니다.");
					status = HttpStatus.NOT_FOUND;
				}
			}else{ // 기존에 좋아요 한 경우
				logger.info("=====> 기존 좋아요 한 게시글");
				int result = placeService.clickUnlike(param);
				if(result != 0){	
					placeService.minusPost(lid);		
					logger.info("=====> 핫플레이스 게시글 좋아요 취소 성공");
					resultMap.put("message", "핫플레이스 게시글 좋아요 취소 성공하였습니다.");
					status = HttpStatus.ACCEPTED;
				}else{
					logger.info("=====> 핫플레이스 게시글 좋아요 취소 실패");
					resultMap.put("message", "핫플레이스 게시글 좋아요 취소 실패하였습니다.");
					status = HttpStatus.NOT_FOUND;
				}
			}			
        } catch (Exception e) {
            logger.error("게시글 좋아요 처리 실패 : {}", e);
			resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

	/*
     * 기능: 핫플레이스 리스트
     * 
     * developer: 윤수민
     * 
     * @param : p_location, sort(new,popular)
     * 
     * @return : message,
     * postList(lid, p_latitude, p_longtitude, l_image, l_desc, l_date, pe_name) 
     */
	@ApiOperation(value = "HotPlace postList", notes = "핫플레이스 게시글 리스트")
    @GetMapping("/list/{p_location}")
    public ResponseEntity<Map<String, Object>> getPostByList(@PathVariable("p_location") String p_location, @RequestParam(value = "sort") String sort) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
			logger.info("place/list 호출성공");

            if(sort.equals("pop")){ // 인기순 정렬
                List<Map<String, Object>> postList = placeService.getPostListLike(p_location);
                if(postList != null){
                    resultMap.put("postList", postList);
                    resultMap.put("message", "좋아요순 핫플레이스 게시글 리스트 호출 성공하였습니다.");
                    status = HttpStatus.ACCEPTED;
                }else{
                    resultMap.put("message", "핫플레이스 게시글 리스트가 없습니다.");
                    status = HttpStatus.NOT_FOUND;
                }
            }else{ // 최신순 정렬
                List<Map<String, Object>> postList = placeService.getPostListNew(p_location);
                if(postList != null){
                    resultMap.put("postList", postList);
                    resultMap.put("message", "최신순 핫플레이스 게시글 리스트 호출 성공하였습니다.");
                    status = HttpStatus.ACCEPTED;
                }else{
                    resultMap.put("message", "핫플레이스 게시글 리스트가 없습니다.");
                    status = HttpStatus.NOT_FOUND;
                }
            }
            
            
        } catch (Exception e) {
            logger.error("게시글 리스트 호출 실패 : {}", e);
			resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    /*
     * 기능: 핫플레이스 TOP5
     * 
     * developer: 윤수민
     * 
     * @param : p_location
     * 
     * @return : message,
     * postList(lid, p_latitude, p_longtitude, l_image, l_desc, l_date, pe_name)
     */
	@ApiOperation(value = "HotPlace top5", notes = "핫플레이스 TOP5")
    @GetMapping("/top5/{p_location}")
    public ResponseEntity<Map<String, Object>> getTop5(@PathVariable("p_location") String p_location) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
			logger.info("place/top5 호출성공");

            List<Map<String, Object>> postList = placeService.getTop5(p_location);
            if(postList != null){
                resultMap.put("postList", postList);
                resultMap.put("message", "핫플레이스 TOP5 호출 성공하였습니다.");
                status = HttpStatus.ACCEPTED;
            }else{
                resultMap.put("message", "핫플레이스 리스트가 없습니다.");
                status = HttpStatus.NOT_FOUND;
            }
            
        } catch (Exception e) {
            logger.error("top5 호출 실패 : {}", e);
			resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    /*
     * 기능: 핫플레이스 포스트 디테일
     * 
     * developer: 윤수민
     * 
     * @param : lid
     * 
     * @return : message,
     * postList(lid, p_latitude, p_longtitude, l_image, l_desc, l_date, pe_name)
     */
	@ApiOperation(value = "HotPlace detail", notes = "핫플레이스 디테일")
    @GetMapping("/detail/{lid}")
    public ResponseEntity<Map<String, Object>> getDetail(@PathVariable("lid") int lid) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
			logger.info("place/detail 호출성공");

            Map<String, Object> postDetail = placeService.getDetail(lid);
            if(postDetail != null){
                resultMap.put("postDetail", postDetail);
                resultMap.put("message", "핫플레이스 디테일 호출 성공하였습니다.");
                status = HttpStatus.ACCEPTED;
            }else{
                resultMap.put("message", "핫플레이스가 없습니다.");
                status = HttpStatus.NOT_FOUND;
            }
            
        } catch (Exception e) {
            logger.error("핫플레이스 디테일 호출 실패 : {}", e);
			resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}
