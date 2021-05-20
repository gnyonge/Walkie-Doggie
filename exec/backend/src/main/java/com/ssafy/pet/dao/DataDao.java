package com.ssafy.pet.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataDao {

    // 지역명 가져오기
    String getLocation(String peid);

    // 산책 횟수
    Integer getLWalkCount(String location);

    Integer getPWalkCount(String peid);

    // 총 산책 시간
    Integer getLTotalTime(String location);
    
    Integer getPTotalTime(String peid);    

    // 산책 시간대
    Integer getLWalkTime(String location);
    
    Integer getPWalkTime(String peid);
    
}
