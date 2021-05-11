package com.ssafy.pet.service;

import java.util.List;
import java.util.Map;

import com.ssafy.pet.dto.LikePlaceDto;

public interface PlaceService {

    // 장소 좋아요
    public Integer checkPlace(Map<String, Object> param);

    public int createPlace(Map<String, Object> param);

    public int clickPlace(Map<String, Object> param);

    public int plusPlace(int pid);

    public int checkLike(Map<String, Object> map);

    // 핫플레이스 게시글 수정 및 삭제
	public int isWriter(Map<String, Object> map);

    public int modifyPlace(LikePlaceDto likePlaceDto);

	public int deletePlace(int lid);

	public int minusPlace(int pid);

    // 게시글 좋아요
    public Integer checkLikePost(Map<String, Object> param);

    public int clickLike(Map<String, Object> param);

    public int clickUnlike(Map<String, Object> param);

    public void plusPost(int lid);

    public void minusPost(int lid);

    // 게시글 리스트
    public List<Map<String, Object>> getPostListNew(String p_location);

    public List<Map<String, Object>> getPostListLike(String p_location);

    // Top5
    public List<Map<String, Object>> getTop5(String p_location);
    
}
