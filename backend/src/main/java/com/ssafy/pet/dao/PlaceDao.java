package com.ssafy.pet.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlaceDao {

    public Integer checkPlace(Map<String, Object> param);

    public int createPlace(Map<String, Object> param);

    public int clickPlace(Map<String, Object> param);

    public int plusPlace(int pid);

    public int checkLike(Map<String, Object> map);
    
}
