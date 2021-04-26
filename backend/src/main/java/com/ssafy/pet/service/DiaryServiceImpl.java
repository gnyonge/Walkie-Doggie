package com.ssafy.pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pet.dao.DiaryDao;
import com.ssafy.pet.dto.DiaryDto;

@Service
public class DiaryServiceImpl implements DiaryService {

	@Autowired
	DiaryDao ddao;

	@Override
	public int insert(DiaryDto diary) {
		return ddao.insert(diary);
	}

}
