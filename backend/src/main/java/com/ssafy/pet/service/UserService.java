package com.ssafy.pet.service;

import java.util.List;

import com.ssafy.pet.dto.EmailAuthDto;
import com.ssafy.pet.dto.PetDto;
import com.ssafy.pet.dto.UserDto;

public interface UserService {
	
	int signup(UserDto user);

	int check_nick(String nick);
	
	UserDto checkEmail(String email);
	List<PetDto> petInfo(String uid);
	int setAddress(String uid, String address);
	int leaveUser(String uid, String password);
	UserDto checkPass(String uid);
	int changePass(String uid, String pass);
	EmailAuthDto checkAuth(String email); 
	int insertAuth(EmailAuthDto temp);
	int updateAuth(EmailAuthDto temp);
	int updateFlag(String email);
	
	EmailAuthDto checkEmailAuth(String email);
	
	}
