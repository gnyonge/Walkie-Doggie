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
	public int insert_diary(DiaryDto diary) {
		return ddao.insert_diary(diary);
	}

	@Override
	public DiaryDto get_diary(String d_date) {
		return ddao.get_diary(d_date);
	}

	@Override
	public int update_diary(DiaryDto diary) {
		return ddao.update_diary(diary);
	}

	@Override
	public int update_pic(DiaryDto diary) {
		return ddao.update_pic(diary);
	}

	@Override
	public int delete_diary(DiaryDto diary) {
		return ddao.delete_diary(diary);
	}

}
