package com.example.tt_nsk.tournament;

import com.example.tt_nsk.dto.PlayerBriefRepresentationDto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Getter
@Builder
public class TournamentData {

    private static final int SCORE_DIFFERENCE = 2;
    private List<List<String>> legUpTable;
    private List<Game> gamesList;
    //private int setsToWinGame;

    public List<Integer> columns() {
        List<Integer> integers = new ArrayList<>();
        for (int i = 1; i < legUpTable.size() + 1; i++) {
            integers.add(i);
        }

        return integers;
    }

    public Optional<String> getLegUp(int coordinateX, int coordinateY) {
        if (coordinateY > legUpTable.size() - 1 || coordinateX > legUpTable.get(coordinateY).size() - 1) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(legUpTable.get(coordinateY).get(coordinateX));
        }
    }

    public boolean setLegUp(int coordinateX, int coordinateY, String result) {
        if (coordinateY > legUpTable.size() - 1 || coordinateX > legUpTable.get(coordinateY).size() - 1) {
            return false;
        } else {
            legUpTable.get(coordinateY).add(coordinateX, result);
            return true;
        }
    }

    @RequiredArgsConstructor
    @Getter
    @JsonPropertyOrder({"gameStatus", "gameWinner", "playerPair", "playSetList"})
    public static class Game {

        enum GameStatus {NOT_STARTED_YET, IS_BEING_PLAYED, FINISHED}
        private GameStatus gameStatus = GameStatus.NOT_STARTED_YET;
        private final Pair<PlayerBriefRepresentationDto, PlayerBriefRepresentationDto> playerPair;
        private final List<PlaySet> playSetList = new ArrayList<>();
        private PlayerBriefRepresentationDto gameWinner = null;


        public boolean addPlaySet(int firstPlayerResult, int secondPlayerResult) {
            if ((Math.abs(firstPlayerResult - secondPlayerResult) < SCORE_DIFFERENCE)
                    || gameStatus.equals(GameStatus.FINISHED)) {
                return false;
            } else {
                playSetList.add(new PlaySet(firstPlayerResult, secondPlayerResult));
                defineWinnerAndGameStatus();
                return true;
            }
        }

        private void defineWinnerAndGameStatus() {
            int firstPlayerWonSets = 0;
            int secondPlayerWonSets = 0;

            for (PlaySet playSet : playSetList) {
                if (playSet.firstPlayerResult > playSet.secondPlayerResult) {
                    firstPlayerWonSets++;
                } else {
                    secondPlayerWonSets++;
                }
            }

            if (Math.abs(firstPlayerWonSets - secondPlayerWonSets) >= CurrentTournament.getInstance().getSetsToWinGame()) {
                gameStatus = GameStatus.FINISHED;
                if (firstPlayerWonSets > secondPlayerWonSets) {
                    gameWinner = playerPair.getFirst();
                } else {
                    gameWinner = playerPair.getSecond();
                }
            } else if (!playSetList.isEmpty()) {
                gameStatus = GameStatus.IS_BEING_PLAYED;
            }
        }
    }

    @AllArgsConstructor
    @Getter
    public static class PlaySet {
        private final int firstPlayerResult;
        private final int secondPlayerResult;
    }

}

