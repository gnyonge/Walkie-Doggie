package com.ssafy.pet.service;

import com.ssafy.pet.dto.UserDto;

public interface LoginService {
	UserDto check_email(String email);

}
