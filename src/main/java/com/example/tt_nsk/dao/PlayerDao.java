package com.example.tt_nsk.dao;

import com.example.tt_nsk.entity.Player;
import com.example.tt_nsk.entity.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerDao extends JpaRepository<Player, Long> {
    List<Player> findAllByStatus(Status status);
    List<Player> findAllByStatus(Status status, Pageable pageable);
    List<Player> findAllByStatus(Status status, Sort sort);

    Optional<Player> findByLastname(String title);
//    List<Player> findAllByTitleContaining(String title);

}
