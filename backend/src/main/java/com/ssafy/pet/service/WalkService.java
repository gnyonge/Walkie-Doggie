package com.ssafy.pet.service;

import java.util.Map;

import com.ssafy.pet.dto.PlaceDto;
import com.ssafy.pet.dto.WalkDto;

public interface WalkService {

    public int createWalk(WalkDto walkDto);

    public int saveRoute(Map<String, Object> map);

    public int saveEnd(Map<String, Object> map);

    public Integer checkPlace(Map<String, Object> param);

    public int createPlace(Map<String, Object> param);

    public int clickPlace(Map<String, Object> param);

    public int plusPlace(int pid);

    public int checkLike(Map<String, Object> map);

}
