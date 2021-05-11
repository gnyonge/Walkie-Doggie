package com.ssafy.pet.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;
@Component
public class SecurityUtil {
	
	//SHA-256 해싱 메소드
	public byte[] sha256(String msg) throws NoSuchAlgorithmException {
	    MessageDigest md = MessageDigest.getInstance("SHA-256");
	    //해시 함수가 출력하는 압축된 문장 : Digest
	    md.update(msg.getBytes());
	    
	    return md.digest();
	}
	//바이트를 헥사값으로 변환
	public String bytesToHex(byte[] bytes) {
	    StringBuilder builder = new StringBuilder();
	    for (byte b: bytes) {
	      builder.append(String.format("%02x", b));
	    }
	    return builder.toString();
	}


}
