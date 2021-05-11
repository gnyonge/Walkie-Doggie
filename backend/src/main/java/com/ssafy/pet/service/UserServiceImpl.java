package com.ssafy.pet.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pet.dao.UserDao;
import com.ssafy.pet.dto.PetDto;
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
	
	@Override
	public UserDto checkEmail(String email) {
		// TODO Auto-generated method stub
		return udao.checkEmail(email);
	}
	
	@Override
	public List<PetDto> petInfo(String uid) {
		// TODO Auto-generated method stub
		return udao.petInfo(uid);
	}
	
	@Override
	public int setAddress(String uid, String address) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("uid", uid);
		map.put("address", address);
		return udao.setAddress(map);
	}
	
	@Override
	public int leaveUser(String uid, String password) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("uid", uid);
		map.put("password", password);
		return udao.leaveUser(map);
	}


}
