package com.ssafy.pet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.pet.dto.HealthDto;

@Mapper
public interface HealthDao {

	int insert_health(HealthDto health); // 건강 등록

	List<HealthDto> get_health(String h_date); // 건강 조회

	int update_health(HealthDto health); // 건강 수정

	int delete_health(HealthDto health); // 건강 삭제

}
