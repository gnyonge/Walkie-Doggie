package com.ssafy.pet.dao;

import java.util.Map;

import com.ssafy.pet.dto.WalkDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WalkDao {

    public int createWalk(WalkDto walkDto);

    public int saveRoute(Map<String, Object> map);

}
