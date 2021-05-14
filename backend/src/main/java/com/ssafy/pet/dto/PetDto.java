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
public class PetDto {
	
	//반려동물 DTO 
	
	private String peid;
	private String uid;
	private String pe_name;
	private String pe_profile_photo;
	private String pe_age;
	private String pe_weight;
	private String pe_trauma;
	private String pe_disease;
	private String pe_birthday;
	private int pe_flag;
	

}
