package com.example.tt_nsk.tournament;

import com.example.tt_nsk.dto.PlayerBriefRepresentationDto;
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
    private int setsToWinGame;

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
    public static class Game {
        private final Pair<PlayerBriefRepresentationDto, PlayerBriefRepresentationDto> playerPair;
        private final List<PlaySet> playSets = new ArrayList<>();

        public boolean addPlaySet(int firstPlayerResult, int secondPlayerResult) {
            if (Math.abs(firstPlayerResult - secondPlayerResult) < SCORE_DIFFERENCE) {
                return false;
            } else {
                playSets.add(new PlaySet(firstPlayerResult, secondPlayerResult));
                return true;
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

