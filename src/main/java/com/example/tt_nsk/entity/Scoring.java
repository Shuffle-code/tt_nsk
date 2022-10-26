package com.example.tt_nsk.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
//@NoArgsConstructor
@RequiredArgsConstructor
public class Scoring {
    public int score;
    public int set;
    public double delta;
    public int place;
}
