package com.example.tt_nsk.dao;

import com.example.tt_nsk.entity.UpcomingTournamentData;
import liquibase.pro.packaged.Q;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UpcomingTournamentDataRepo extends CrudRepository<UpcomingTournamentData, Long> {
    //List<UpcomingTournamentData> findAllByRegistrationEndsGreaterThan(Timestamp now);
    List<UpcomingTournamentData> findAllByRegistrationEndsIsAfter(Timestamp now);
    @Query(nativeQuery = true, value = "SELECT ID, tour_id, total_players, reg_ends FROM upcoming_tournament_data ORDER BY ID DESC LIMIT 1")
    UpcomingTournamentData findLast();

    @Query(nativeQuery = true, value = "SELECT total_players FROM upcoming_tournament_data WHERE tour_id = :tourId")
    Optional<Integer> findTotalPlayersByTourId(Long tourId);
}
