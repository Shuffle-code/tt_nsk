package com.example.tt_nsk.dao;

import com.example.tt_nsk.entity.Tour;
import com.example.tt_nsk.entity.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface TourDao extends JpaRepository<Tour, Long> {
//    List<Tour> findAllByStatus(Status status, Sort sort);
    List<Tour> findAllByStatus(Status status, Pageable pageable);
    List<Tour> findAllByStatus(Status status, Sort sort);
    Tour findTourByStatus(Status status);

    Optional<Tour> findByTitle(String title);
    List<Tour> findAllByTitleContaining(String title);

    @Query(nativeQuery = true, value = "SELECT id, title, date, winner_id, address_id, amount_players, VERSION, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, STATUS, RESULT_TOUR FROM tournament WHERE date >= :date")
    List<Tour> findUpcomingTournaments(Date date);
}
