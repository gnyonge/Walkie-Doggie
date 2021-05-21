package com.ssafy.pet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.pet.dto.AllergyDto;
import com.ssafy.pet.dto.HealthDto;
import com.ssafy.pet.dto.PetDto;

@Mapper
public interface PetDao {

	String check_add(String uid); // 반려견 등록 가능 여부 확인

	int regist_pet(PetDto pet); // 반려견 등록

	List<HealthDto> show_health(String peid); // 반려견 건강 정보 조회

	PetDto show_pet(String peid); // 반려견 정보 조회

	int leave_pet(String peid); // 반려견 삭제

	int update_pet(PetDto pet); // 반려견 정보 수정

	int insert_allergy(AllergyDto allergy); // 알레르기 등록

	List<AllergyDto> show_allergy(String peid); // 알레르기 조회

	int delete_allergy(AllergyDto allergy); // 알레르기 삭제

}
