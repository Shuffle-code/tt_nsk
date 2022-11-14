package com.example.tt_nsk.service;

import com.example.tt_nsk.dao.PlayerDao;
import com.example.tt_nsk.entity.Player;
import com.example.tt_nsk.entity.Score;
import com.example.tt_nsk.entity.Scoring;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//@Service
@RequiredArgsConstructor
@Slf4j
public class PlayService {
    private final Scoring scoring;
    private final Score score;
    private final PlayerDao playerDao;

    public int sumSet(Player player,Score score1, Score score2, Score score3, Score score4) {
        int set;
        return 0;
    }
    @Transactional(propagation = Propagation.NEVER, isolation = Isolation.DEFAULT)
    public int countSet() {
        int score = scoring.score;
        return scoring.score;
    }

    public Player saveRating(Player player) {
        if (player.getId() != null) {
            Optional<Player> playerFromDBOptional = playerDao.findById(player.getId());
            if (playerFromDBOptional.isPresent()) {
                Player playerFromDB = playerFromDBOptional.get();
                playerFromDB.setRating(player.getRating());
                return playerDao.save(playerFromDB);
            }
        }
        return playerDao.save(player);
    }

    public double newRating;
}
//String[] parts = firstLine.split(" ");!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
