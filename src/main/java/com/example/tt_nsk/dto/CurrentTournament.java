package com.example.tt_nsk.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrentTournament {

    private final List<PlayerBriefRepresentationDto> players;
    private final String[][] results;

   private CurrentTournament(BUILDER builder){
        players = builder.players;
        results = builder.results;
    }

   public Optional<String> getResult(int coordinateX, int coordinateY) {
       if (coordinateX > results[0].length - 1 || coordinateY > results.length - 1) {
           return Optional.empty();
       } else {
           return Optional.ofNullable(results[coordinateX][coordinateY]);
       }
   }

    public boolean setResult(int coordinateX, int coordinateY, String result) {
        if (coordinateX > results[0].length - 1 || coordinateY > results.length - 1) {
            return false;
        } else {
            results[coordinateX][coordinateY] = result;
            return true;
        }
    }


    public static class BUILDER {

        private List<PlayerBriefRepresentationDto> players;
        private String[][] results;

        public static BUILDER newBuilder() {
            return new BUILDER();
        }


        public BUILDER players(List<PlayerBriefRepresentationDto> players) {
            this.players = players;
            this.results = new String[players.size()][players.size()];
            for (int i = 0; i < results.length; i++){
                results[i][i] = "TT";
            }
            return this;
        }

        public CurrentTournament build(){
            return new CurrentTournament(this);
        }
    }
}
