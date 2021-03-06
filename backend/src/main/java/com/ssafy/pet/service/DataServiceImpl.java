package com.ssafy.pet.service;

import com.ssafy.pet.dao.DataDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService{

    @Autowired
	DataDao ddao;

    // 지역명 가져오기
    @Override
    public String getLocation(String peid) {
        return ddao.getLocation(peid);
    }

    // 산책 횟수
    @Override
    public Integer getLWalkCount(String location) {
        return ddao.getLWalkCount(location);
    }

    @Override
    public Integer getPWalkCount(String peid) {
        return ddao.getPWalkCount(peid);
    }

    // 총 산책 시간
    @Override
    public Integer getLTotalTime(String location) {
        return ddao.getLTotalTime(location);
    }

    @Override
    public Integer getPTotalTime(String peid) {
        return ddao.getPTotalTime(peid);
    }

    // 산책 시간대
    @Override
    public Integer getLWalkTime(String location) {
        return ddao.getLWalkTime(location);
    }

    @Override
    public Integer getPWalkTime(String peid) {
        return ddao.getPWalkTime(peid);
    }

}
