package com.example.tt_nsk.service;

import com.example.tt_nsk.dao.PlayerDao;
import com.example.tt_nsk.entity.Player;
import com.example.tt_nsk.entity.PlayerImage;
import com.example.tt_nsk.entity.enums.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerService {
    private final PlayerDao playerDao;
    private final PlayerImageService playerImageService;
    @Transactional(propagation = Propagation.NEVER, isolation = Isolation.DEFAULT)
    public Long count() {
        return playerDao.count();
    }

    @Transactional(propagation = Propagation.NEVER, isolation = Isolation.DEFAULT)
    public Integer countPlaying() {
        return playerDao.findAllByStatus(Status.ACTIVE).size();
    }

    public Player save(Player player, File file) {
        if (player.getId() != null) {
            Optional<Player> playerFromDBOptional = playerDao.findById(player.getId());
            if (playerFromDBOptional.isPresent()) {
                Player playerFromDB = playerFromDBOptional.get();
                playerFromDB.setFirstname(player.getFirstname());
                playerFromDB.setPatronymic(player.getPatronymic());
                playerFromDB.setRating(player.getRating());
                playerFromDB.setRatingTtw(player.getRatingTtw());
                playerFromDB.setYearOfBirth(player.getYearOfBirth());
                playerFromDB.setStatus(player.getStatus());
                return playerDao.save(playerFromDB);
            }
        }
        return playerDao.save(player);
    }

    public Long maxId(){
        return playerDao.maxId();
    }

//    @Transactional
    public Player save(Player player, MultipartFile multipartFile) {
//        Product product = productMapper.toProduct(productDto, manufacturerDao, categoryDao);
//        Product productFromDB = productDao.getById(productDto.getId()); // todo при создании нового продукта id=null
//        if (productFromDB != null && !productDto.getCost().equals(productFromDB.getCost())) sendMessage(productDto);
        if (player.getId() != null) {
            playerDao.findById(player.getId()).ifPresent(
                    (p) -> player.setVersion(p.getVersion())
            );
        }
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String pathToSavedFile = playerImageService.save(multipartFile);
            PlayerImage playerImage = PlayerImage.builder()
                    .path(pathToSavedFile)
                    .player(player)
                    .build();
            player.addImage(playerImage);
        }
        return playerDao.save(player);
    }


    @Transactional
    public Player save(final Player player) {
        return save(player, (MultipartFile) null);
    }

    public List<String> getIdTtw(){
        return playerDao.getIdTtw();
    }


    public List<Player> findAll() {
        return playerDao.findAll();
    }

    public List<Player> findAllActive() {
        return playerDao.findAllByStatus(Status.ACTIVE);
    }

    public void deleteById(Long id) {
        try {
            playerDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
        }
    }

    public void statusDelete(Long id) {
        Optional<Player> Player = playerDao.findById(id);
        Player.ifPresent(p -> {
            p.setStatus(Status.DELETED);
            playerDao.save(p);
        });
    }

    public void disable(Long id) {
        Optional<Player> Player = playerDao.findById(id);
        Player.ifPresent(p -> {
            p.setStatus(Status.DISABLE);
            playerDao.save(p);
        });
    }
    public List<Player> findAll(int page, int size) {
        return playerDao.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size));
    }
    @Transactional(readOnly = true)
    public List<Player> findAllActiveSortedById() {
        return playerDao.findAllByStatus(Status.ACTIVE, Sort.by(Sort.Direction.DESC,"id"));
    }
    @Transactional(readOnly = true)
    public List<Player> findAllActiveSortedByRating() {
        return playerDao.findAllByStatus(Status.ACTIVE, Sort.by(Sort.Direction.DESC,"rating"));
    }
    @Transactional(readOnly = true)
    public List<Player> findAllDisableSortedByRating() {
        return playerDao.findAllByStatus(Status.DISABLE, Sort.by(Sort.Direction.DESC,"rating"));
    }
    @Transactional(readOnly = true)
    public List<Player> findAllNotActiveSortedByRating() {
        return playerDao.findAllByStatus(Status.NOT_ACTIVE, Sort.by(Sort.Direction.DESC,"rating"));
    }

    public List<Player> addListForMainPage(){
        List<Player> players = Stream
                .of(findAllActiveSortedByRating(), findAllNotActiveSortedByRating(), findAllDisableSortedByRating())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return players;
    }

    @Transactional(readOnly = true)
    public List<Player> findAllSortedById(int page, int size) {
        return playerDao.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size, Sort.by("id")));
    }
    @Transactional(readOnly = true)
    public List<Player> findAllSortedByRating() {
        return playerDao.findAll(Sort.by(Sort.Direction.DESC,"rating"));
    }

    public Player getPlayerFromTour(int i){
        Player player = findAllSortedByRating().get(i);
        return player;
    }

    public String stringPlayer(Player player){
        String str = player.getFirstname() + player.getLastname() + player.getRating();
        return str;
    }

//    @Transactional(readOnly = true)
//    public PlayerDto findById(Long id) {
//        return playerMapper.toPlayerDto(playerDao.findById(id).orElse(null));
//    }
    @Transactional(readOnly = true)
    public Player findById(Long id) {
    return playerDao.findById(id).orElse(null);
}
}
