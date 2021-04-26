package com.ssafy.pet.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.pet.dto.UserDto;

@Mapper
public interface UserDao {
	
	int signup(UserDto user);
	
	int check_nick(String nick);

}
