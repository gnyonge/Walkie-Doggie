package com.ssafy.pet.dao;

import com.ssafy.pet.dto.WalkDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WalkDao {
    public int createWalk(WalkDto walkDto);
}
