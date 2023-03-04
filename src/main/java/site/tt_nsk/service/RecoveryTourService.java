package site.tt_nsk.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.tt_nsk.entity.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecoveryTourService {

    public List<List<String>> compileScoreForUpcomingTour(String scoreFromDb, List<Player> playerList) {
        if (playerList.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<String> listScore = new ArrayList<>(Arrays.asList(scoreFromDb.split(", ")));
        List<String> withoutField = new ArrayList();
        for (String cellScore : listScore) {
            String[] split = cellScore.split("=");
            withoutField.add(split[1]);
        }
//        x1y2='3.2', x1y3='3/1', x2y1='2/3', x2y3='2/3', x3y1='1/3', x3y2='3/2'
        List<List<String>> resultTable = new ArrayList<>();
        int count = 0;
        for (int y = 0; y < playerList.size(); y++) {
            List<String> row = new ArrayList<>();
            count = count + playerList.size();
            for (int x = 0; x < playerList.size(); x++) {
                if (y == x) {
                    row.add("TT");
                } else {
                    row.add(withoutField.get((x + count) - 1));
                }
            }
            resultTable.add(row);
        }
        return resultTable;
    }
}
