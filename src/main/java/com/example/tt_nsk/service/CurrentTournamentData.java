package com.example.tt_nsk.service;

import com.example.tt_nsk.dto.CurrentTournament;

import java.util.Objects;

public class CurrentTournamentData {
    private static boolean tourStarted = false;
    private static CurrentTournament tournament = null;

    public static void startTour(CurrentTournament _tournament){
        if (Objects.isNull(tournament)) {
            tournament = _tournament;
        }
        tourStarted = true;
    }

    public static boolean hasTourStarted(){
        return tourStarted;
    }

    public  static CurrentTournament tournament(){
        return tournament;
    }

}

