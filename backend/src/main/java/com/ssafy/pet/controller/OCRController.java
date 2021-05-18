package com.ssafy.pet.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.pet.dto.AllergyDto;
import com.ssafy.pet.dto.IngredientsDto;
import com.ssafy.pet.service.OCRService;
import com.ssafy.pet.service.PetService;

import net.sourceforge.tess4j.Tesseract;

@RestController
@RequestMapping("/ocr")
public class OCRController {

	public static final Logger logger = LoggerFactory.getLogger(OCRController.class);

	@Autowired
	OCRService oservice;

	@Autowired
	PetService pservice;

	@PostMapping()
	public ResponseEntity<Map<String, Object>> traduzir(@RequestParam(name = "file") MultipartFile file,
			@RequestParam String peid) throws Exception {
		logger.info("ocr 호출성공");

		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		if (!"png".equals(ext) && !"jpg".equals(ext) && !"JPG".equals(ext)) {
			return ResponseEntity.badRequest().build();
		}

		String resultado = "";

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			String path = System.getProperty("user.dir");
			path = path + "\\tessdata";

			// 배포시 경로 변경
//			path.replace("\\", "\\\\");

			BufferedImage img = ImageIO.read(file.getInputStream());
			Tesseract tesseract = new Tesseract();
			tesseract.setDatapath(path);
			resultado = "";
			tesseract.setLanguage("kor");
			resultado = tesseract.doOCR(img);

			String[] ingredient_list = { "향료", "가수분해", "쌀", "생선", "옥수수", "글루텐", "가금", "동물성", "양조", "유도단백질", "어분", "마이즈",
					"부산물", "육분" };

			List<IngredientsDto> ing_list = new ArrayList<>();
			for (String ingredient : ingredient_list) {
				if (resultado.contains(ingredient)) {
					ing_list.add(oservice.get_ingredient(ingredient));
				}
			}
			logger.info("=====> 유해 성분 확인 성공");

			List<AllergyDto> allergy_list = pservice.show_allergy(peid);

			List<String> all_list = new ArrayList<>();
			if(allergy_list != null){
				for (AllergyDto allergy : allergy_list) {
					if (resultado.contains(allergy.getAl_name())) {
						all_list.add(allergy.getAl_name());
					}
				}
				resultMap.put("Allergys", all_list);
				logger.info("=====> 알레르기 확인 성공");
			}else{
				resultMap.put("Allergys", "");
				logger.info("=====> 알레르기 없음");
			}
			

			resultMap.put("Ingredients", ing_list);
			
			resultMap.put("message", "OCR 확인에 성공하였습니다.");
			status = HttpStatus.ACCEPTED;

		} catch (IOException e) {
			logger.error("OCR 확인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			throw new Exception("Erro ao ler arquivo");
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}