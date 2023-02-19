package com.example.tt_nsk.dao;

import com.example.tt_nsk.entity.security.PlayerTournament;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PlayerTournamentRepo extends CrudRepository<PlayerTournament, Long> {

    Iterable<PlayerTournament> findAllByPlayerId(Long playerId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM player_tournament WHERE player_id = :playerId AND tournament_id = :tournamentId")
    void disenroll(@Param("playerId") Long playerId, @Param("tournamentId") Long tournamentId);

    List<PlayerTournament> findAllByTournamentIdOrderByPlayerId(Long tournamentId);

    List<PlayerTournament> findAllByTournamentId(Long tournamentId);
}
