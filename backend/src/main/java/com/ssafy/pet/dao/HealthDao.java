package com.ssafy.pet.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.pet.dto.HealthDto;

@Mapper
public interface HealthDao {

	int insert(HealthDto health);

}
