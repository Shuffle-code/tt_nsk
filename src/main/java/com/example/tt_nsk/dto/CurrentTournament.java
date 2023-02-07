package com.example.tt_nsk.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Getter
@Builder
public class CurrentTournament {

    private final List<PlayerBriefRepresentationDto> players;
    private final List<List<String>> resultTable;
    private int currentRaw = 0;

   public List<Integer> columns() {
        List<Integer> integers = new ArrayList<>();
        for (int i = 1; i < resultTable.size() + 1; i++) {
            integers.add(i);
        }

        return integers;
   }

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
}
