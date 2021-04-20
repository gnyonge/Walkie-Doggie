package com.ssafy.pet.dto;

public class PostDto {

	private int poid;
	private int uid;
	private String po_title;
	private String po_content;
	private String po_category;
	private String po_date;
	private int po_flag;
	private String po_location;
	private int po_count;

	public PostDto(int poid, int uid, String po_title, String po_content, String po_category, String po_date,
			int po_flag, String po_location, int po_count) {
		super();
		this.poid = poid;
		this.uid = uid;
		this.po_title = po_title;
		this.po_content = po_content;
		this.po_category = po_category;
		this.po_date = po_date;
		this.po_flag = po_flag;
		this.po_location = po_location;
		this.po_count = po_count;
	}

	public int getPoid() {
		return poid;
	}

	public void setPoid(int poid) {
		this.poid = poid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getPo_title() {
		return po_title;
	}

	public void setPo_title(String po_title) {
		this.po_title = po_title;
	}

	public String getPo_content() {
		return po_content;
	}

	public void setPo_content(String po_content) {
		this.po_content = po_content;
	}

	public String getPo_category() {
		return po_category;
	}

	public void setPo_category(String po_category) {
		this.po_category = po_category;
	}

	public String getPo_date() {
		return po_date;
	}

	public void setPo_date(String po_date) {
		this.po_date = po_date;
	}

	public int getPo_flag() {
		return po_flag;
	}

	public void setPo_flag(int po_flag) {
		this.po_flag = po_flag;
	}

	public String getPo_location() {
		return po_location;
	}

	public void setPo_location(String po_location) {
		this.po_location = po_location;
	}

	public int getPo_count() {
		return po_count;
	}

	public void setPo_count(int po_count) {
		this.po_count = po_count;
	}

	@Override
	public String toString() {
		return "PostDto [poid=" + poid + ", uid=" + uid + ", po_title=" + po_title + ", po_content=" + po_content
				+ ", po_category=" + po_category + ", po_date=" + po_date + ", po_flag=" + po_flag + ", po_location="
				+ po_location + ", po_count=" + po_count + "]";
	}

}
