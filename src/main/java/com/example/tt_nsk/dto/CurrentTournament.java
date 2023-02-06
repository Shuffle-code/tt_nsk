package com.example.tt_nsk.dto;

import lombok.Data;
import lombok.Getter;

import java.nio.charset.Charset;
import java.util.*;

@Data
@Getter
public class CurrentTournament {

    private final List<PlayerBriefRepresentationDto> players;
    private final List<List<String>> resultTable;
    private int currentRaw = 0;

    private CurrentTournament(BUILDER builder) {
        players = builder.players;
        resultTable = builder.resultTable;
    }

//    public List<String> getFirst(){
//        return resultTable.get(0);
//    }
//
//    public List<String> getNext(){
//        if (currentRaw < resultTable.size() - 1) {
//            return resultTable.get(currentRaw++);
//        } else {
//            return Collections.emptyList();
//        }
//
//    }


    public Optional<String> getResult(int coordinateX, int coordinateY) {
        if (coordinateY > resultTable.size() - 1 || coordinateX > resultTable.get(coordinateY).size() - 1) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(resultTable.get(coordinateY).get(coordinateX));
        }
    }

    public boolean setResult(int coordinateX, int coordinateY, String result) {
        if (coordinateY > resultTable.size() - 1 || coordinateX > resultTable.get(coordinateY).size() - 1) {
            return false;
        } else {
            resultTable.get(coordinateY).add(coordinateX, result);
            return true;
        }
    }


    public static class BUILDER {

        private List<PlayerBriefRepresentationDto> players;
        private List<List<String>> resultTable = new ArrayList<>();

        public static BUILDER newBuilder() {
            return new BUILDER();
        }


        public BUILDER players(List<PlayerBriefRepresentationDto> players) {
            this.players = players;
            for (int vert = 0; vert < players.size(); vert++) {
                List<String> raw = new ArrayList<>();
                for (int hor = 0; hor < players.size(); hor++) {
                    if (vert == hor) {
                        raw.add("TT");
                    } else {
                        raw.add("NP");
                    }

                }
                resultTable.add(raw);
            }

            return this;
        }

        public CurrentTournament build() {
            return new CurrentTournament(this);
        }
    }
}
