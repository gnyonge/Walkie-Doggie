package com.ssafy.pet.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.pet.dto.DiaryDto;

@Mapper
public interface DiaryDao {

	int insert(DiaryDto diary);

}
