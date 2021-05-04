package com.ssafy.pet.service;

import java.util.Map;

import com.ssafy.pet.dao.WalkDao;
import com.ssafy.pet.dto.PlaceDto;
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

    @Override
    public Integer checkPlace(Map<String, Object> param) {
        return sqlSession.getMapper(WalkDao.class).checkPlace(param);
    }

    @Override
    public int createPlace(Map<String, Object> param) {
        return sqlSession.getMapper(WalkDao.class).createPlace(param);
    }

    @Override
    public int clickPlace(Map<String, Object> param) {
        return sqlSession.getMapper(WalkDao.class).clickPlace(param);
    }

    @Override
    public int plusPlace(int pid) {
        return sqlSession.getMapper(WalkDao.class).plusPlace(pid);
    }

    @Override
    public int checkLike(Map<String, Object> map) {
        return sqlSession.getMapper(WalkDao.class).checkLike(map);
    }
}
