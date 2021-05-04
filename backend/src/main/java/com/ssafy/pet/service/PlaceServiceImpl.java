package com.ssafy.pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.ssafy.pet.dao.PlaceDao;

@Service
public class PlaceServiceImpl implements PlaceService{

    @Autowired
	PlaceDao pdao;

	@Override
    public Integer checkPlace(Map<String, Object> param) {
        return pdao.checkPlace(param);
    }

    @Override
    public int createPlace(Map<String, Object> param) {
        return pdao.createPlace(param);
    }

    @Override
    public int clickPlace(Map<String, Object> param) {
        return pdao.clickPlace(param);
    }

    @Override
    public int plusPlace(int pid) {
        return pdao.plusPlace(pid);
    }

    @Override
    public int checkLike(Map<String, Object> map) {
        return pdao.checkLike(map);
    }

}
