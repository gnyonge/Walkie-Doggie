package com.ssafy.pet.dto;

public class IngredientsDto {

	private int iid;
	private String i_name;
	private String i_desc;
	private int i_flag;

	public IngredientsDto(int iid, String i_name, String i_desc, int i_flag) {
		super();
		this.iid = iid;
		this.i_name = i_name;
		this.i_desc = i_desc;
		this.i_flag = i_flag;
	}

	public int getIid() {
		return iid;
	}

	public void setIid(int iid) {
		this.iid = iid;
	}

	public String getI_name() {
		return i_name;
	}

	public void setI_name(String i_name) {
		this.i_name = i_name;
	}

	public String getI_desc() {
		return i_desc;
	}

	public void setI_desc(String i_desc) {
		this.i_desc = i_desc;
	}

	public int getI_flag() {
		return i_flag;
	}

	public void setI_flag(int i_flag) {
		this.i_flag = i_flag;
	}

	@Override
	public String toString() {
		return "IngredientsDto [iid=" + iid + ", i_name=" + i_name + ", i_desc=" + i_desc + ", i_flag=" + i_flag + "]";
	}

}
