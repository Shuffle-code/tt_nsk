package com.example.tt_nsk.dao;

import com.example.tt_nsk.entity.security.PlayerTournament;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerTournamentRepo extends CrudRepository<PlayerTournament, Long> {

    Iterable<PlayerTournament> findAllByPlayerId(Long playerId);
}
