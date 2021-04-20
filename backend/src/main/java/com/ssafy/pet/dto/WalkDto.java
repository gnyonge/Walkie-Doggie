package com.ssafy.pet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalkDto {
    private int wid;
    private String peid;
    private String w_img;
    private float w_direction;
    private String w_time;
    private String w_date;
    private int w_flag;
}