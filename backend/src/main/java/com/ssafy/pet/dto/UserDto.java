package com.ssafy.pet.dto;

public class UserDto {
	
	// 유저 테이블 
	private String uid;
	private String u_email;
	private String u_password;
	private String u_location;
	private String u_nickname;
	private int u_flag;
	private int u_report;
	public UserDto(String uid, String u_email, String u_password, String u_location, String u_nickname, int u_flag,
			int u_report) {
		super();
		this.uid = uid;
		this.u_email = u_email;
		this.u_password = u_password;
		this.u_location = u_location;
		this.u_nickname = u_nickname;
		this.u_flag = u_flag;
		this.u_report = u_report;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getU_email() {
		return u_email;
	}
	public void setU_email(String u_email) {
		this.u_email = u_email;
	}
	public String getU_password() {
		return u_password;
	}
	public void setU_password(String u_password) {
		this.u_password = u_password;
	}
	public String getU_location() {
		return u_location;
	}
	public void setU_location(String u_location) {
		this.u_location = u_location;
	}
	public String getU_nickname() {
		return u_nickname;
	}
	public void setU_nickname(String u_nickname) {
		this.u_nickname = u_nickname;
	}
	public int getU_flag() {
		return u_flag;
	}
	public void setU_flag(int u_flag) {
		this.u_flag = u_flag;
	}
	public int getU_report() {
		return u_report;
	}
	public void setU_report(int u_report) {
		this.u_report = u_report;
	}
	@Override
	public String toString() {
		return "UserDto [uid=" + uid + ", u_email=" + u_email + ", u_password=" + u_password + ", u_location="
				+ u_location + ", u_nickname=" + u_nickname + ", u_flag=" + u_flag + ", u_report=" + u_report + "]";
	}
	
	
	
	
	
	

}
