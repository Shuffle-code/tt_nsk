package com.example.tt_nsk.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data
@Getter
@Setter
public class PlayerBriefRepresentationDto {
    private Long playerId;
    private String firstname;
    private String patronymic;
    private String lastname;
    private Double rating;
    private Double delta;
    private String sets;
    private Integer place;
}
