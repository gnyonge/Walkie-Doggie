package com.ssafy.pet.dto;

public class CommentDto {

	private int cid;
	private int poid;
	private String c_uid;
	private String c_date;
	private String c_content;
	private int c_flag;
	
	
	public CommentDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommentDto(int cid, int poid, String c_uid, String c_date, String c_content, int c_flag) {
		super();
		this.cid = cid;
		this.poid = poid;
		this.c_uid = c_uid;
		this.c_date = c_date;
		this.c_content = c_content;
		this.c_flag = c_flag;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getPoid() {
		return poid;
	}
	public void setPoid(int poid) {
		this.poid = poid;
	}
	public String getC_uid() {
		return c_uid;
	}
	public void setC_uid(String c_uid) {
		this.c_uid = c_uid;
	}
	public String getC_date() {
		return c_date;
	}
	public void setC_date(String c_date) {
		this.c_date = c_date;
	}
	public String getC_content() {
		return c_content;
	}
	public void setC_content(String c_content) {
		this.c_content = c_content;
	}
	public int getC_flag() {
		return c_flag;
	}
	public void setC_flag(int c_flag) {
		this.c_flag = c_flag;
	}
	@Override
	public String toString() {
		return "CommentDto [cid=" + cid + ", poid=" + poid + ", c_uid=" + c_uid + ", c_date=" + c_date + ", c_content="
				+ c_content + ", c_flag=" + c_flag + "]";
	}
	
	
}
