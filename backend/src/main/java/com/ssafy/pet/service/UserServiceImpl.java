package com.ssafy.pet.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pet.dao.UserDao;
import com.ssafy.pet.dto.EmailAuthDto;
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
	
	@Override
	public UserDto checkPass(String uid) {
		// TODO Auto-generated method stub
		return udao.checkPass(uid);
	}
	@Override
	public int changePass(String uid, String pass) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("uid", uid);
		map.put("password", pass);
		return udao.changePass(map);
	}
	
	@Override
	public EmailAuthDto checkAuth(String email) {
		// TODO Auto-generated method stub
		return udao.checkAuth(email);
	}
	
	@Override
	public int insertAuth(EmailAuthDto temp) {
		// TODO Auto-generated method stub
		return udao.insertAuth(temp);
	}
	
	@Override
	public int updateAuth(EmailAuthDto temp) {
		// TODO Auto-generated method stub
		return udao.updateAuth(temp);
	}
	
	@Override
	public int updateFlag(String email) {
		// TODO Auto-generated method stub
		return udao.updateFlag(email);
	}
	
	@Override
	public EmailAuthDto checkEmailAuth(String email) {
		// TODO Auto-generated method stub
		return udao.checkEmailAuth(email);
	}


}
