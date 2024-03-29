package site.tt_nsk.tournament;

import site.tt_nsk.dto.PlayerBriefRepresentationDto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TournamentData {

    private long tuornamentId;
    private static final int SCORE_DIFFERENCE = 2;
    private List<List<String>> legUpTable;
    private List<Game> gamesList;
    private boolean tourStarted = false;
    private int playSetsToWinGame;

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

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @JsonPropertyOrder({"gameStatus", "gameWinner", "playerPair", "playSetList"})
    public static class Game {


        enum GameStatus {NOT_STARTED_YET, IS_BEING_PLAYED, FINISHED}

        private GameStatus gameStatus = GameStatus.NOT_STARTED_YET;
        private PlayerBriefRepresentationDto firstPlayer;
        private PlayerBriefRepresentationDto secondPlayer;
        private List<PlaySet> playSetList = new ArrayList<>();
        private PlayerBriefRepresentationDto gameWinner = null;

        public Game(PlayerBriefRepresentationDto first, PlayerBriefRepresentationDto second) {
            firstPlayer = first;
            secondPlayer = second;
        }

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

            if (Math.abs(firstPlayerWonSets - secondPlayerWonSets) >= CurrentTournament.getInstance().tournamentData().getPlaySetsToWinGame()) {
                gameStatus = GameStatus.FINISHED;
                if (firstPlayerWonSets > secondPlayerWonSets) {
                    gameWinner = firstPlayer;
                } else {
                    gameWinner = secondPlayer;
                }
            } else if (!playSetList.isEmpty()) {
                gameStatus = GameStatus.IS_BEING_PLAYED;
            }
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class PlaySet {
        private int firstPlayerResult;
        private int secondPlayerResult;
    }

}