package com.example.tt_nsk.tournament;

import java.util.Objects;

public class CurrentTournament {

    private TournamentData tournament = null;
    private static final CurrentTournament INSTANCE = new CurrentTournament();


    private CurrentTournament() {

    }

    public static CurrentTournament getInstance() {
        return INSTANCE;
    }

//    public int getSetsToWinGame() {
//        return setsToWinGame;
//    }

    public void startTour(TournamentData _tournament, int _setsToWinGame) {
        if (Objects.isNull(tournament)) {
            tournament = _tournament;
            tournament.setPlaySetsToWinGame(_setsToWinGame);
            tournament.setTourStarted(true);
        }
    }

    public boolean hasTourStarted() {
        if (Objects.isNull(tournament)) {
            return false;
        } else {
            return true;
        }
    }

    public TournamentData tournamentData() {
        return tournament;
    }

}

