package com.ssafy.pet.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.pet.dto.UserDto;

@Mapper
public interface UserDao {
	
	int signup(UserDto user);
	
	int check_nick(String nick);
	
	UserDto checkEmail(String email);
		
//	int authEmail(Map<String, Object> map);

}
