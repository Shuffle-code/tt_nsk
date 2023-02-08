package com.example.tt_nsk.dto;

import lombok.*;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Getter
@Builder
public class CurrentTournament {

    //private final List<PlayerBriefRepresentationDto> players;
    private final List<List<String>> legUpTable;
    private final List<Game> gamesList;

   public List<Integer> columns() {
        List<Integer> integers = new ArrayList<>();
        for (int i = 1; i < legUpTable.size() + 1; i++) {
            integers.add(i);
        }

        return integers;
   }

    public Optional<String> getResult(int coordinateX, int coordinateY) {
        if (coordinateY > legUpTable.size() - 1 || coordinateX > legUpTable.get(coordinateY).size() - 1) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(legUpTable.get(coordinateY).get(coordinateX));
        }
    }

    public boolean setResult(int coordinateX, int coordinateY, String result) {
        if (coordinateY > legUpTable.size() - 1 || coordinateX > legUpTable.get(coordinateY).size() - 1) {
            return false;
        } else {
            legUpTable.get(coordinateY).add(coordinateX, result);
            return true;
        }
    }

    @RequiredArgsConstructor
    @Getter
    public static class Game{
        private final Pair<PlayerBriefRepresentationDto, PlayerBriefRepresentationDto> playerPair;
        private final Long[] playSets = new Long[]{0L, 0L, 0L};

        public boolean setWinner(Long winnerOrderInPair, int playSetOrder){
            if (playSetOrder < 0 && playSetOrder > 3) {
                return false;
            }
            if (winnerOrderInPair !=1 & winnerOrderInPair !=2){
                return false;
            }
            playSets[playSetOrder] = winnerOrderInPair;
            return true;
        }
    }
}
