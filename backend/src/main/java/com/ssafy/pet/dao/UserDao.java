package com.ssafy.pet.dao;

<<<<<<< HEAD
=======
import java.util.List;
import java.util.Map;

>>>>>>> frontend
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.pet.dto.PetDto;
import com.ssafy.pet.dto.UserDto;

@Mapper
public interface UserDao {
	
	int signup(UserDto user);
	
	int check_nick(String nick);
<<<<<<< HEAD
=======
	
	UserDto checkEmail(String email);
	List<PetDto> petInfo(String uid);
		
//	int authEmail(Map<String, Object> map);
>>>>>>> frontend

}
