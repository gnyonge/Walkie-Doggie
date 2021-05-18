package com.ssafy.pet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pet.dao.OCRDao;
import com.ssafy.pet.dto.HealthDto;
import com.ssafy.pet.dto.IngredientsDto;

@Service
public class OCRServiceImpl implements OCRService {
	
	@Autowired
	OCRDao odao;

	@Override
	public IngredientsDto get_ingredient(String ingredient) {
		// TODO Auto-generated method stub
		return odao.get_ingredient(ingredient);
	}
	

}
