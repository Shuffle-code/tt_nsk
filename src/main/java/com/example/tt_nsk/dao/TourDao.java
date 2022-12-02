package com.example.tt_nsk.dao;

import com.example.tt_nsk.entity.Tour;
import com.example.tt_nsk.entity.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TourDao extends JpaRepository<Tour, Long> {
//    List<Tour> findAllByStatus(Status status, Sort sort);
    List<Tour> findAllByStatus(Status status, Pageable pageable);
    List<Tour> findAllByStatus(Status status, Sort sort);
    Tour findTourByStatus(Status status);

    Optional<Tour> findByTitle(String title);
    List<Tour> findAllByTitleContaining(String title);

}
