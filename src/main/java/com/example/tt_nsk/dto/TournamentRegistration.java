package com.example.tt_nsk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
public class TournamentRegistration {

    private final List<Long> registeredIdList;

    boolean isRegistered(Long tournamentId){
        return registeredIdList.contains(tournamentId);
    }
}
