package com.ssafy.pet.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.pet.dto.IngredientsDto;

@Mapper
public interface OCRDao {

	IngredientsDto get_ingredient(String ingredient);

}