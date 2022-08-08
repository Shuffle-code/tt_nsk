package com.example.tt_nsk.dao;

import com.example.tt_nsk.entity.PlayerImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourImageDao extends JpaRepository<PlayerImage, Long> {

    @Query(value = "SELECT tour_image.path FROM tour_image WHERE tour_image.tour_id = :id LIMIT 1", nativeQuery = true)
    String findImageNameByTourId(@Param("id") Long id);

    @Query(value = "SELECT tour_image.path FROM tour_image WHERE tour_image.id = :id LIMIT 1", nativeQuery = true)
    String findImageNameByImageId(@Param("id") Long id);

    @Query(value = "SELECT tour_image.id from tour_image WHERE tour_image.tour_id = :id", nativeQuery = true)
    List<Long> findAllIdImagesByTourId(@Param("id") Long id);

}
