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
public class WalkDto {
    private int wid;
    private String peid;
    private String w_img;
    private float w_distance;
    private int w_time;
    private String w_date;
    private int w_flag;
}