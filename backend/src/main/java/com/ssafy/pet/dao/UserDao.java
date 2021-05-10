package com.ssafy.pet.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.pet.dto.PetDto;
import com.ssafy.pet.dto.UserDto;

@Mapper
public interface UserDao {
	
	int signup(UserDto user);
	
	int check_nick(String nick);
	
	UserDto checkEmail(String email);
	List<PetDto> petInfo(String uid);
	int setAddress(Map<String, Object> map);
	int leaveUser(Map<String, Object> map);
}
