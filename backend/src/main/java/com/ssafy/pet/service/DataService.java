package com.ssafy.pet.service;

public interface DataService {

    // 지역명 가져오기
    String getLocation(String peid);

    // 산책 횟수
    int getLWalkCount(String location);

    int getPWalkCount(String peid);

}
