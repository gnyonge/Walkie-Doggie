package com.ssafy.pet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.pet.dto.AllergyDto;
import com.ssafy.pet.dto.HealthDto;
import com.ssafy.pet.dto.PetDto;

@Mapper
public interface PetDao {
	
	String check_add(String uid);
	int regist_pet(PetDto pet);
	List<HealthDto> show_health(String peid);
	PetDto show_pet(String peid);
	int leave_pet(String peid);
	int update_pet(PetDto pet);
	int insert_allergy(AllergyDto allergy);

}
