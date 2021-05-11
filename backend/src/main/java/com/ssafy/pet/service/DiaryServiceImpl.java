package com.ssafy.pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
	public DiaryDto get_diary(String peid, String d_date) {
		return ddao.get_diary(peid, d_date);
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
	
	@Override
	public List<String> get_image(Map<String, Object> map) {
		return ddao.get_image(map);
	}

	@Override
	public List<Map<String, Object>> get_walk(Map<String, Object> map) {
		return ddao.get_walk(map);
	}
}
