package com.ssafy.pet.service;

import java.util.List;

import com.ssafy.pet.dto.HealthDto;

public interface HealthService {

	int insert_health(HealthDto health); // 건강 등록

	List<HealthDto> get_health(String h_date); // 건강 조회

	int update_health(HealthDto health); // 건강 수정

	int delete_health(HealthDto health); // 건강 삭제

}
