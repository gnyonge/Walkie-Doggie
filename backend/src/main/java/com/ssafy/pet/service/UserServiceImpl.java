package com.ssafy.pet.service;

<<<<<<< HEAD
=======
import java.util.HashMap;
import java.util.List;
import java.util.Map;

>>>>>>> frontend
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
	
//	@Override
//	public int authEmail(String email, String auth) {
//		// TODO Auto-generated method stub
//		Map<String, Object> map = new HashMap<>();
//		map.put("email", email);
//		map.put("auth", auth);
//		return udao.authEmail(map);
//	}

}
