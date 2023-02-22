package com.example.tt_nsk.dao;

import com.example.tt_nsk.entity.UpcomingTournamentData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;

public interface UpcomingTournamentDataRepo extends CrudRepository<UpcomingTournamentData, Long> {
    List<UpcomingTournamentData> findAllByRegistrationEndsGreaterThan(Timestamp now);
    @Query(nativeQuery = true, value = "SELECT ID, tour_id, total_players, reg_ends FROM upcoming_tournament_data ORDER BY ID DESC LIMIT 1")
    UpcomingTournamentData findLast();
}
