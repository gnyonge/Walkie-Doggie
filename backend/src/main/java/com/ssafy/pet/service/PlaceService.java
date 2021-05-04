package com.ssafy.pet.service;

import java.util.Map;

import com.ssafy.pet.dto.LikePlaceDto;

public interface PlaceService {

    public Integer checkPlace(Map<String, Object> param);

    public int createPlace(Map<String, Object> param);

    public int clickPlace(Map<String, Object> param);

    public int plusPlace(int pid);

    public int checkLike(Map<String, Object> map);

	public int isWriter(Map<String, Object> map);

    public int modifyPlace(LikePlaceDto likePlaceDto);
    
}
