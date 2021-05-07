package com.ssafy.pet.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.pet.dto.UserDto;

@Mapper
public interface LoginDao {
	UserDto check_email(String email);

}
