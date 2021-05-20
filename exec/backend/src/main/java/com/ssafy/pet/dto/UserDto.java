package com.ssafy.pet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
	
	// 유저 테이블 
	private String uid;
	private String u_email;
	private String u_password;
	private String u_location;
	private String u_nickname;
	private int u_flag;
	private int u_report;
	
	public UserDto(String uid, String u_email, String u_nickname) {
		super();
		this.uid = uid;
		this.u_email = u_email;
		this.u_nickname = u_nickname;
	}
	
	
	
	
	
	

}
