package com.ssafy.pet.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.sourceforge.tess4j.Tesseract;

@RestController
@RequestMapping("/ocr")
public class OCRController {
	
	@PostMapping()
	public ResponseEntity<String> traduzir(@RequestParam(name="file") MultipartFile file) throws Exception{
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		if (!"png".equals(ext) && !"jpg".equals(ext)) {
			return ResponseEntity.badRequest().build();
		}
		String resultado = "";
		
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
			
			System.out.println(resultado);
		} catch (IOException e) {
			throw new Exception("Erro ao ler arquivo");
		}
		return ResponseEntity.ok(resultado);						
	}

}