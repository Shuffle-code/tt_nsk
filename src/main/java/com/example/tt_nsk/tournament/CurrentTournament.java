package com.example.tt_nsk.tournament;

import java.util.Objects;

public class CurrentTournament {
    private static boolean tourStarted = false;
    private static TournamentData tournament = null;
    private static final CurrentTournament INSTANCE = new CurrentTournament();

    private CurrentTournament(){

    }

    public static CurrentTournament getInstance(){
        return INSTANCE;
    }



    public static void startTour(TournamentData _tournament){
        if (Objects.isNull(tournament)) {
            tournament = _tournament;
        }
        tourStarted = true;
    }

    public static boolean hasTourStarted(){
        return tourStarted;
    }

    public  static TournamentData tournamentData(){
        return tournament;
    }

}

