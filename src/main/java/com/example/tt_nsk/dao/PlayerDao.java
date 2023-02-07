package com.example.tt_nsk.dao;

import com.example.tt_nsk.entity.Player;
import com.example.tt_nsk.entity.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlayerDao extends JpaRepository<Player, Long> {
    List<Player> findAllByStatus(Status status);
    List<Player> findAllByStatus(Status status, Pageable pageable);
    List<Player> findAllByStatus(Status status, Sort sort);
    @Query(value = "SELECT MAX(id) FROM player ", nativeQuery = true)
    Long maxId();

    @Query(value = "SELECT ID_TTWR FROM nsk_tt.player where ID_TTWR != 'null' & ID_TTWR != ''", nativeQuery = true)
    List<String> getIdTtw();

    @Query(nativeQuery = true, value = "SELECT id, firstname, patronymic, lastname, " +
            "rating, rating_ttw, year_of_birth, VERSION, CREATED_BY, CREATED_DATE, " +
            "LAST_MODIFIED_BY, LAST_MODIFIED_DATE, STATUS, TOUR_ID, ID_TTWR " +
            "FROM player WHERE id IN :ids ORDER BY rating DESC")
    List<Player> findAllByIdsOrderByRatingDesc(@Param("ids") List<Long> ids);



//    Player findFirstByIdOrderByPointPointsDesc();

    Optional<Player> findByLastname(String title);
//    List<Player> findAllByTitleContaining(String title);

}
