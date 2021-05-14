package com.ssafy.pet.service;

import com.ssafy.pet.dao.DataDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService{

    @Autowired
	DataDao ddao;

}
