package com.ssafy.pet.dao;

import java.util.Map;

import com.ssafy.pet.dto.LikePlaceDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlaceDao {

    public Integer checkPlace(Map<String, Object> param);

    public int createPlace(Map<String, Object> param);

    public int clickPlace(Map<String, Object> param);

    public int plusPlace(int pid);

    public int checkLike(Map<String, Object> map);

	public int isWriter(Map<String, Object> map);

    public int modifyPlace(LikePlaceDto likePlaceDto);

	public int deletePlace(int lid);

	public int minusPlace(int pid);
    
}
