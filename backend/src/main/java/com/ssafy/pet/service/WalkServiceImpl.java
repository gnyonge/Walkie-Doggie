package com.ssafy.pet.service;

import java.util.Map;

import com.ssafy.pet.dao.WalkDao;
import com.ssafy.pet.dto.WalkDto;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalkServiceImpl implements WalkService{

    @Autowired
    private SqlSession sqlSession;

    @Override
    public int createWalk(WalkDto walkDto) {
        return sqlSession.getMapper(WalkDao.class).createWalk(walkDto);
    }

    @Override
    public int saveRoute(Map<String, Object> map) {
        return sqlSession.getMapper(WalkDao.class).saveRoute(map);
    }

    @Override
    public int saveEnd(Map<String, Object> map) {
        return sqlSession.getMapper(WalkDao.class).saveEnd(map);
    }
}
