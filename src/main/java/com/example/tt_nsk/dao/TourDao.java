package com.example.tt_nsk.dao;

import com.example.tt_nsk.entity.Tour;
import com.example.tt_nsk.entity.enums.Status;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
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

    @Query(nativeQuery = true, value = "SELECT id, title, date, winner_id, address_id, amount_players, VERSION, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, STATUS, RESULT_TOUR, scoring FROM tournament WHERE date >= :date")
    List<Tour> findUpcomingTournaments(Date date);

    @Query(nativeQuery = true, value = "SELECT current_tournament FROM tournament WHERE id = :id")
    Optional<String> findCurrentTournamentById(@Param("id") long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE tournament SET current_tournament =:toBeSaved WHERE id =:id")
    Integer updateCurrentTournamentById(@Param("toBeSaved")String toBeSaved, @Param("id")long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE tournament SET scoring =:score WHERE id =:id")
    void updateTour(@Param("score") String score, @Param("id")long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE tournament SET scoring =:score, RESULT_TOUR =:scoring, STATUS =:status, winner_id =:winnerId WHERE id =:id")
    void updateTourAfterSave(@Param("score") String score, @Param("scoring") String scoring,
                             @Param("status") String status, @Param("winnerId")long winnerId,
                             @Param("id")long id);

}
