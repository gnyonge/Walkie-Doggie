package com.ssafy.pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.ssafy.pet.dao.PlaceDao;
import com.ssafy.pet.dto.LikePlaceDto;

@Service
public class PlaceServiceImpl implements PlaceService{

    @Autowired
	PlaceDao pdao;

    // 장소 좋아요
	@Override
    public Integer checkPlace(Map<String, Object> param) {
        return pdao.checkPlace(param);
    }

    @Override
    public int createPlace(Map<String, Object> param) {
        return pdao.createPlace(param);
    }

    @Override
    public Integer clickPlace(Map<String, Object> param) {
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

    // 핫플레이스 게시글 수정 및 삭제
    @Override
    public int isWriter(Map<String, Object> map) {
        return pdao.isWriter(map);
    }

    @Override
    public int modifyPlace(LikePlaceDto likePlaceDto) {
        return pdao.modifyPlace(likePlaceDto);
    }

    @Override
    public int getPid(int lid) {
        return pdao.getPid(lid);
    }

    @Override
    public int deletePlace(int lid) {
        return pdao.deletePlace(lid);
    }

    @Override
    public int minusPlace(int pid) {
        return pdao.minusPlace(pid);
    }

    // 게시글 좋아요
    @Override
    public Integer checkLikePost(Map<String, Object> param) {
        return pdao.checkLikePost(param);
    }

    @Override
    public int clickLike(Map<String, Object> param) {
        return pdao.clickLike(param);
    }

    @Override
    public int clickUnlike(Map<String, Object> param) {
        return pdao.clickUnlike(param);
    }

    @Override
    public void plusPost(int lid) {
        pdao.plusPost(lid);
    }

    @Override
    public void minusPost(int lid) {
        pdao.minusPost(lid);
    }

    // 게시글 리스트
    @Override
    public List<Map<String, Object>> getPostListNew(String p_location, String uid) {
        return pdao.getPostListNew(p_location, uid);
    }

    @Override
    public List<Map<String, Object>> getPostListLike(String p_location, String uid) {
        return pdao.getPostListLike(p_location, uid);
    }
    
    @Override
    public Map<String, Object> getDetail(int lid) {
        return pdao.getDetail(lid);
    }

    // Top5
    @Override
    public List<Map<String, Object>> getTop5(String p_location) {
        return pdao.getTop5(p_location);
    }

}
