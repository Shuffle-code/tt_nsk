package com.example.tt_nsk.tournament;

import java.util.Objects;

public class CurrentTournament {
    private boolean tourStarted = false;
    private TournamentData tournament = null;
    private static final CurrentTournament INSTANCE = new CurrentTournament();
    private int setsToWinGame;

    private CurrentTournament(){

    }

    public static CurrentTournament getInstance(){
        return INSTANCE;
    }

    public int getSetsToWinGame() {
        return setsToWinGame;
    }

    public  void startTour(TournamentData _tournament, int _setsToWinGame){
        if (Objects.isNull(tournament)) {
            tournament = _tournament;
            setsToWinGame = _setsToWinGame;
        }
        tourStarted = true;
    }

    public boolean hasTourStarted(){
        return tourStarted;
    }

    public TournamentData tournamentData(){
        return tournament;
    }

}

