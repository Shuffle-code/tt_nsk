package site.tt_nsk.service;

import site.tt_nsk.entity.Pair;
import site.tt_nsk.entity.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@Service
@RequiredArgsConstructor
@Slf4j
public class PairService {

    public ArrayList<Pair> getListOrderGames(ArrayList<Player> listPlayersForTour) {
        Map<Integer, Pair> orderGames = new HashMap<>();
        int count = 1;
        while (listPlayersForTour.size() != 1) {
            int j = 0;
            for (int i = 1; i < listPlayersForTour.size(); i++) {
                Pair pair = new Pair();
                pair.setPlayer1(listPlayersForTour.get(i));
                pair.setPlayer2(listPlayersForTour.get(j));
                orderGames.put(count, pair);
                count ++;
            }
            listPlayersForTour.remove(0);
        }
        Object[] objects = orderGames.values().toArray();
        ArrayList<Pair> pairArrayList = new ArrayList<>();
        for (Object p : objects) {
            Pair pair = Pair.class.cast(p);
            pairArrayList.add(pair);
        }
        return pairArrayList;
    }


}
