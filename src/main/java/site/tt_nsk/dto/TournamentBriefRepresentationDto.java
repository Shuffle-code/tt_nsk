package site.tt_nsk.dto;

import site.tt_nsk.entity.Address;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

//@Data
@Getter
@Setter
public class TournamentBriefRepresentationDto {

    private Long id;
    private String title;
    private Date date;
    private Address address;
    private Long amountPlayers;
    private boolean registered;
}

