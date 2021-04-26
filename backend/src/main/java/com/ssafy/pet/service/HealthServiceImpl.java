package com.ssafy.pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pet.dao.HealthDao;
import com.ssafy.pet.dto.HealthDto;

@Service
public class HealthServiceImpl implements HealthService {

	@Autowired
	HealthDao hdao;

	@Override
	public int insert(HealthDto health) {
		// TODO Auto-generated method stub
		return hdao.insert(health);
	}

}
