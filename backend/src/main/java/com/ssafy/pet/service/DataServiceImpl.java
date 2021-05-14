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
    public int getLWalkCount(String location) {
        return ddao.getLWalkCount(location);
    }

    @Override
    public int getPWalkCount(String peid) {
        return ddao.getPWalkCount(peid);
    }

}
