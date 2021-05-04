package com.ssafy.pet.service;

import com.ssafy.pet.dto.UserDto;

public interface UserService {
	
	int signup(UserDto user);

	int check_nick(String nick);
}
