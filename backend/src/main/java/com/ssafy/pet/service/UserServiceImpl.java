package com.ssafy.pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pet.dao.UserDao;
import com.ssafy.pet.dto.UserDto;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDao udao;
	
	@Override
	public int signup(UserDto user) {
		// TODO Auto-generated method stub
		return udao.signup(user);
	}
	
	@Override
	public int check_nick(String nick) {
		// TODO Auto-generated method stub
		return udao.check_nick(nick);
	}

}
