package com.ssafy.pet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikePlaceDto {
    private int pid;
    private String peid;
    private int isLike;
    private int lp_flag;
}