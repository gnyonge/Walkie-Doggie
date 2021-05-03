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
public class LikePlaceDto {
    private int lid;
    private int pid;
    private String peid;
    private int uid;
    private int l_like;
    private int l_hate;
    private String l_image;
    private String l_desc;
    private int l_flag;
}