package com.ssafy.pet.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.pet.dto.DiaryDto;

@Mapper
public interface DiaryDao {

	int insert_diary(DiaryDto diary); // 기록일지 등록

	DiaryDto get_diary(String peid, String d_date); // 기록일지 조회

	int update_diary(DiaryDto diary); // 기록일지 수정 (사진 제외)

	int update_pic(DiaryDto diary); // 기록일지 수정 (사진 포함)

	int delete_diary(DiaryDto diary); // 기록일지 삭제
	
    List<String> get_image(Map<String, Object> map); // 산책 경로 목록

    List<Map<String, Object>> get_walk(Map<String, Object> map); // 산책 정보 목록
    
    List<String> get_all_diary(String peid); // 전체 기록일지 조회

}
