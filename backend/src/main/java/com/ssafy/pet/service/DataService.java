package com.ssafy.pet.service;

public interface DataService {

    // 지역명 가져오기
    String getLocation(String peid);

    // 산책 횟수
    int getLWalkCount(String location);

    int getPWalkCount(String peid);

    // 총 산책 시간
    int getLTotalTime(String location);

    int getPTotalTime(String peid);

    // 산책 시간대
    int getLWalkTime(String location);

    int getPWalkTime(String peid);

}
