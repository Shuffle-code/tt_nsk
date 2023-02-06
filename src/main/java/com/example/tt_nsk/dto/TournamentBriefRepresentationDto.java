package com.example.tt_nsk.dto;

import com.example.tt_nsk.entity.Address;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Data
@Getter
@Setter
public class TournamentBriefRepresentationDto {

    private Long id;
    private String title;
    private Date date;
    private Address address;
    private boolean registered;
}
