package com.ssafy.pet.dao;

import java.util.Map;

import com.ssafy.pet.dto.PlaceDto;
import com.ssafy.pet.dto.WalkDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WalkDao {

    public int createWalk(WalkDto walkDto);

    public int saveRoute(Map<String, Object> map);

    public int saveEnd(Map<String, Object> map);

    public int checkPlace(Map<String, Object> param);

    public int createPlace(Map<String, Object> param);

    public int clickPlace(Map<String, Object> param);

    public int getPid(Map<String, Object> param);

    public int minusPlace(int pid);

    public int plusPlace(int pid);

}
