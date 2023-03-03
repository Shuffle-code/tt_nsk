package site.tt_nsk.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.tt_nsk.entity.TourImage;

import java.util.List;

public interface TourImageDao extends JpaRepository<TourImage, Long> {

    @Query(value = "SELECT tour_image.path FROM tour_image WHERE tour_image.tour_id = :id LIMIT 1", nativeQuery = true)
    String findImageNameByTourId(@Param("id") Long id);

    @Query(value = "SELECT tour_image.path FROM tour_image WHERE tour_image.id = :id LIMIT 1", nativeQuery = true)
    String findImageNameByImageId(@Param("id") Long id);

    @Query(value = "SELECT tour_image.id from tour_image WHERE tour_image.tour_id = :id", nativeQuery = true)
    List<Long> findAllIdImagesByTourId(@Param("id") Long id);

    @Query(value = "SELECT tour_image.tour_id from tour_image WHERE tour_image.id = :id", nativeQuery = true)
    Long findTourIdByImageId(Long id);

    @Override
    void deleteById(Long aLong);


}