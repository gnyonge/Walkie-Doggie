package com.ssafy.pet.service;

import java.util.Map;

import com.ssafy.pet.dto.WalkDto;

public interface WalkService {

    public int createWalk(WalkDto walkDto);

    public int saveRoute(Map<String, Object> map);

}
