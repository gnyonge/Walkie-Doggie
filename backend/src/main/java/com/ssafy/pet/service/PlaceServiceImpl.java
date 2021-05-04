package com.ssafy.pet.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.ssafy.pet.dao.PlaceDao;

@Service
public class PlaceServiceImpl implements PlaceService{
	
	@Autowired
    private SqlSession sqlSession;

	@Override
    public Integer checkPlace(Map<String, Object> param) {
        return sqlSession.getMapper(PlaceDao.class).checkPlace(param);
    }

    @Override
    public int createPlace(Map<String, Object> param) {
        return sqlSession.getMapper(PlaceDao.class).createPlace(param);
    }

    @Override
    public int clickPlace(Map<String, Object> param) {
        return sqlSession.getMapper(PlaceDao.class).clickPlace(param);
    }

    @Override
    public int plusPlace(int pid) {
        return sqlSession.getMapper(PlaceDao.class).plusPlace(pid);
    }

    @Override
    public int checkLike(Map<String, Object> map) {
        return sqlSession.getMapper(PlaceDao.class).checkLike(map);
    }

}
