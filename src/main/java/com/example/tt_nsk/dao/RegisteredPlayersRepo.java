package com.example.tt_nsk.dao;

import com.example.tt_nsk.entity.RegisteredPlayer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface RegisteredPlayersRepo extends CrudRepository<RegisteredPlayer, Long> {

    List<RegisteredPlayer> findAllByPlayerId(Long playerId);

    @Query(nativeQuery = true, value = "SELECT DISTINCT tour_id FROM registered_players")
    List<Long> findDistinctTourIds();

    @Transactional
    @Modifying
    void deleteByPlayerIdAndTourId(Long playerId, Long tourId);

    @Procedure("update_status")
    void refreshStatuses(@Param("tourid") Long tourId , @Param("maxplayers") int maxPlayers);
}
