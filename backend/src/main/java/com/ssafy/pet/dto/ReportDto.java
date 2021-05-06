package com.ssafy.pet.dto;


public class ReportDto {
	
	//신고테이블
	
	private int poid;
	private String po_uid;
	private String r_uid;
	private String r_content;
	public ReportDto(int poid, String po_uid, String r_uid, String r_content) {
		super();
		this.poid = poid;
		this.po_uid = po_uid;
		this.r_uid = r_uid;
		this.r_content = r_content;
	}
	
	public int getPoid() {
		return poid;
	}
	public void setPoid(int poid) {
		this.poid = poid;
	}
	public String getPo_uid() {
		return po_uid;
	}
	public void setPo_uid(String po_uid) {
		this.po_uid = po_uid;
	}



	public String getR_uid() {
		return r_uid;
	}



	public void setR_uid(String r_uid) {
		this.r_uid = r_uid;
	}



	public String getR_content() {
		return r_content;
	}



	public void setR_content(String r_content) {
		this.r_content = r_content;
	}



	@Override
	public String toString() {
		return "ReportDto [poid=" + poid + ", po_uid=" + po_uid + ", r_uid=" + r_uid + ", r_content=" + r_content + "]";
	}
	
	
	

}
