package com.ssafy.pet.dto;

public class PetDto {
	
	//반려동물 DTO 
	
	private String peid;
	private String uid;
	private String pe_name;
	private String pr_profile_photo;
	private String pe_age;
	private String pe_weight;
	private String pe_trauma;
	private String pe_disease;
	private String pe_brithday;
	private int pe_flag;
	
	public PetDto(String peid, String uid, String pe_name, String pr_profile_photo, String pe_age, String pe_weight,
			String pe_trauma, String pe_disease, String pe_brithday, int pe_flag) {
		super();
		this.peid = peid;
		this.uid = uid;
		this.pe_name = pe_name;
		this.pr_profile_photo = pr_profile_photo;
		this.pe_age = pe_age;
		this.pe_weight = pe_weight;
		this.pe_trauma = pe_trauma;
		this.pe_disease = pe_disease;
		this.pe_brithday = pe_brithday;
		this.pe_flag = pe_flag;
	}
	public String getPeid() {
		return peid;
	}
	public void setPeid(String peid) {
		this.peid = peid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPe_name() {
		return pe_name;
	}
	public void setPe_name(String pe_name) {
		this.pe_name = pe_name;
	}
	public String getPr_profile_photo() {
		return pr_profile_photo;
	}
	public void setPr_profile_photo(String pr_profile_photo) {
		this.pr_profile_photo = pr_profile_photo;
	}
	public String getPe_age() {
		return pe_age;
	}
	public void setPe_age(String pe_age) {
		this.pe_age = pe_age;
	}
	public String getPe_weight() {
		return pe_weight;
	}
	public void setPe_weight(String pe_weight) {
		this.pe_weight = pe_weight;
	}
	public String getPe_trauma() {
		return pe_trauma;
	}
	public void setPe_trauma(String pe_trauma) {
		this.pe_trauma = pe_trauma;
	}
	public String getPe_disease() {
		return pe_disease;
	}
	public void setPe_disease(String pe_disease) {
		this.pe_disease = pe_disease;
	}
	public String getPe_brithday() {
		return pe_brithday;
	}
	public void setPe_brithday(String pe_brithday) {
		this.pe_brithday = pe_brithday;
	}
	public int getPe_flag() {
		return pe_flag;
	}
	public void setPe_flag(int pe_flag) {
		this.pe_flag = pe_flag;
	}
	
	@Override
	public String toString() {
		return "PetDto [peid=" + peid + ", uid=" + uid + ", pe_name=" + pe_name + ", pr_profile_photo="
				+ pr_profile_photo + ", pe_age=" + pe_age + ", pe_weight=" + pe_weight + ", pe_trauma=" + pe_trauma
				+ ", pe_disease=" + pe_disease + ", pe_brithday=" + pe_brithday + ", pe_flag=" + pe_flag + "]";
	}
	
	

}
