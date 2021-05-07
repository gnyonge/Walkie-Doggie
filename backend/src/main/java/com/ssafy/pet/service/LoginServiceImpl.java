package com.ssafy.pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pet.dao.LoginDao;
import com.ssafy.pet.dto.UserDto;
@Service
public class LoginServiceImpl implements LoginService{
	@Autowired
	private LoginDao ldao;
	
	@Override
	public UserDto check_email(String email) {
		// TODO Auto-generated method stub
		return ldao.check_email(email);
	}

}
