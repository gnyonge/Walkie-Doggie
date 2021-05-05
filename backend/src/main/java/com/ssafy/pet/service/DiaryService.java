package com.ssafy.pet.service;

import com.ssafy.pet.dto.DiaryDto;

public interface DiaryService {

	int insert_diary(DiaryDto diary); // 기록일지 등록

	DiaryDto get_diary(String d_date); // 기록일지 조회

	int update_diary(DiaryDto diary); // 기록일지 수정 (사진 제외)

	int update_pic(DiaryDto diary); // 기록일지 수정 (사진 포함)

	int delete_diary(DiaryDto diary); // 기록일지 삭제

}
