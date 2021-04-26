package com.ssafy.pet.dto;

public class DiaryDto {

	private int peid;
	private String d_memo;
	private String d_img;
	private String d_date;
	private String d_special;
	private int d_walk;
	private int d_flag;

	public DiaryDto(int peid, String d_memo, String d_img, String d_date, String d_special, int d_walk, int d_flag) {
		super();
		this.peid = peid;
		this.d_memo = d_memo;
		this.d_img = d_img;
		this.d_date = d_date;
		this.d_special = d_special;
		this.d_walk = d_walk;
		this.d_flag = d_flag;
	}

	public int getPeid() {
		return peid;
	}

	public void setPeid(int peid) {
		this.peid = peid;
	}

	public String getD_memo() {
		return d_memo;
	}

	public void setD_memo(String d_memo) {
		this.d_memo = d_memo;
	}

	public String getD_img() {
		return d_img;
	}

	public void setD_img(String d_img) {
		this.d_img = d_img;
	}

	public String getD_date() {
		return d_date;
	}

	public void setD_date(String d_date) {
		this.d_date = d_date;
	}

	public String getD_special() {
		return d_special;
	}

	public void setD_special(String d_special) {
		this.d_special = d_special;
	}

	public int getD_walk() {
		return d_walk;
	}

	public void setD_walk(int d_walk) {
		this.d_walk = d_walk;
	}

	public int getD_flag() {
		return d_flag;
	}

	public void setD_flag(int d_flag) {
		this.d_flag = d_flag;
	}

	@Override
	public String toString() {
		return "DiaryDto [peid=" + peid + ", d_memo=" + d_memo + ", d_img=" + d_img + ", d_date=" + d_date
				+ ", d_special=" + d_special + ", d_walk=" + d_walk + ", d_flag=" + d_flag + "]";
	}

}
