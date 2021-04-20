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
public class PlaceDto {
    private int pid;
    private float p_latitude;
    private float p_longtitude;
    private float w_direction;
    private int p_like;
    private int p_hate;
    private int p_flag;
}