package com.ssafy.pet.service;

import java.util.Map;

import com.ssafy.pet.dao.WalkDao;
import com.ssafy.pet.dto.WalkDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalkServiceImpl implements WalkService{

    @Autowired
	WalkDao wdao;

    @Override
    public int createWalk(WalkDto walkDto) {
        return wdao.createWalk(walkDto);
    }

    @Override
    public int saveRoute(Map<String, Object> map) {
        return wdao.saveRoute(map);
    }

}
