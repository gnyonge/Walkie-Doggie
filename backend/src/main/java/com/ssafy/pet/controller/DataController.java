package com.ssafy.pet.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.pet.dto.PlaceDto;
import com.ssafy.pet.dto.WalkDto;
import com.ssafy.pet.service.DataService;
import com.ssafy.pet.service.WalkService;
import com.ssafy.pet.util.S3Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("DataController V1")
@RestController
@RequestMapping("/data")
public class DataController {
	
	public static final Logger logger = LoggerFactory.getLogger(DataController.class);

	@Autowired
    private DataService dataService;

}
