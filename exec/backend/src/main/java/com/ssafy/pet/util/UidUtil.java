package com.ssafy.pet.util;

import org.springframework.stereotype.Component;

@Component
public class UidUtil {
	
	public String MakeUid() {
		StringBuffer made = new StringBuffer();

		for (int i = 0; i < 6; i++) {
			char a = (char) ((Math.random() * 26) + 97); // 소문자
			int ann = (int) (Math.random() * 9) + 1; // 숫자
			made.append(a);
			made.append(ann);
		}

		char b = (char) ((Math.random() * 26) + 97);
		made.append(b);
		String line = made.toString();
		return line;
	}

}
