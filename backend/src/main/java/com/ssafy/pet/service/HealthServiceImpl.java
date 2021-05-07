package com.ssafy.pet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pet.dao.HealthDao;
import com.ssafy.pet.dto.HealthDto;

@Service
public class HealthServiceImpl implements HealthService {

	@Autowired
	HealthDao hdao;

	@Override
	public int insert_health(HealthDto health) {
		return hdao.insert_health(health);
	}

	@Override
	public List<HealthDto> get_health(String h_date) {
		return hdao.get_health(h_date);
	}

	@Override
	public int update_health(HealthDto health) {
		return hdao.update_health(health);
	}

	@Override
	public int delete_health(HealthDto health) {
		return hdao.delete_health(health);
	}

}
