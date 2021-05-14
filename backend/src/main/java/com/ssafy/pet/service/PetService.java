package com.ssafy.pet.service;

import java.util.List;

import com.ssafy.pet.dto.AllergyDto;
import com.ssafy.pet.dto.HealthDto;
import com.ssafy.pet.dto.PetDto;

public interface PetService {
	String check_add(String uid);
	
	int regist_pet(PetDto pet);
	List<HealthDto> show_health(String peid);
	PetDto show_pet(String peid);
	int leave_pet(String peid);
	int update_pet(PetDto pet);
	int insert_allergy(AllergyDto allergy);
	List<AllergyDto> show_allergy(String peid);
	int delete_allergy(AllergyDto allergy);

}
