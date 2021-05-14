package com.ssafy.pet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pet.dao.PetDao;
import com.ssafy.pet.dto.AllergyDto;
import com.ssafy.pet.dto.HealthDto;
import com.ssafy.pet.dto.PetDto;

@Service
public class PetServiceImpl implements PetService {
	@Autowired
	private PetDao pdao;
	
	@Override
	public String check_add(String uid) {
		// TODO Auto-generated method stub
		return pdao.check_add(uid);
	}
	
	@Override
	public int regist_pet(PetDto pet) {
		// TODO Auto-generated method stub
		return pdao.regist_pet(pet);
	}
	@Override
	public List<HealthDto> show_health(String peid) {
		// TODO Auto-generated method stub
		return pdao.show_health(peid);
	}
	
	@Override
	public PetDto show_pet(String peid) {
		// TODO Auto-generated method stub
		return pdao.show_pet(peid);
	}
	
	@Override
	public int leave_pet(String peid) {
		// TODO Auto-generated method stub
		return pdao.leave_pet(peid);
	}
	
	@Override
	public int update_pet(PetDto pet) {
		// TODO Auto-generated method stub
		return pdao.update_pet(pet);
	}
	@Override
	public int insert_allergy(AllergyDto allergy) {
		// TODO Auto-generated method stub
		return pdao.insert_allergy(allergy);
	}
	
	@Override
	public List<AllergyDto> show_allergy(String peid) {
		// TODO Auto-generated method stub
		return pdao.show_allergy(peid);
	}
	
	@Override
	public int delete_allergy(AllergyDto allergy) {
		// TODO Auto-generated method stub
		return pdao.delete_allergy(allergy);
	}
}
