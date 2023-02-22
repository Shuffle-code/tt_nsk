package com.example.tt_nsk.dao;

import com.example.tt_nsk.entity.UpcomingTournamentData;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;

public interface UpcomingTournamentDataRepo extends CrudRepository<UpcomingTournamentData, Long> {
    List<UpcomingTournamentData> findAllByRegistrationEndsGreaterThan(Timestamp now);
}
