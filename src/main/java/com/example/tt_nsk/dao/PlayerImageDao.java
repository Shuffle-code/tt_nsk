package com.example.tt_nsk.dao;

import com.example.tt_nsk.entity.PlayerImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface PlayerImageDao extends JpaRepository<PlayerImage, Long> {

    PlayerImage findFirstByPlayerId(Long id);

    @Query(value = "SELECT player_image.path FROM player_image WHERE player_image.player_id = :id LIMIT 1", nativeQuery = true)
    String findImageNameByPlayerId(@Param("id") Long id);

    @Query(value = "SELECT player_image.path FROM player_image WHERE player_image.id = :id LIMIT 1", nativeQuery = true)
    String findImageNameByImageId(@Param("id") Long id);

    @Query(value = "SELECT player_image.id FROM player_image WHERE player_image.path = :path LIMIT 1", nativeQuery = true)
    Long findImageIdByPath(String path);

    @Query(value = "SELECT player_image.id from player_image WHERE player_image.player_id = :id", nativeQuery = true)
    List<Long> findAllIdImagesByPlayerId(@Param("id") Long id);

    @Query(value = "SELECT MAX(id) FROM player_image ", nativeQuery = true)
    Long maxId();

    @Query(value = "SELECT COUNT(player_id) FROM player_image WHERE player_image.player_id = :id", nativeQuery = true)
    Long count(Long id);

    @Override
    void delete(PlayerImage playerImage);

    @Override
    void deleteById(Long aLong);

}
