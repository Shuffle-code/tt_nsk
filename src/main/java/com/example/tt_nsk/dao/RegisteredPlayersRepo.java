package com.example.tt_nsk.dao;

import com.example.tt_nsk.entity.RegisteredPlayer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface RegisteredPlayersRepo extends CrudRepository<RegisteredPlayer, Long> {

    List<RegisteredPlayer> findAllByPlayerId(Long playerId);

    @Transactional
    @Modifying
    void deleteByPlayerIdAndTourId(Long playerId, Long tourId);

}
