package site.tt_nsk.service;

import site.tt_nsk.dao.PlayerTournamentRepo;
import site.tt_nsk.dao.TourDao;
import site.tt_nsk.entity.Player;
import site.tt_nsk.entity.Tour;
import site.tt_nsk.entity.TourImage;
import site.tt_nsk.entity.enums.Status;
import site.tt_nsk.entity.security.PlayerTournament;
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
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TourService {
    private final TourDao tourDao;
    private final TourImageService tourImageService;
    private final PlayerTournamentRepo playerTournamentRepo;
    private final PlayerService playerService;

    @Transactional(propagation = Propagation.NEVER, isolation = Isolation.DEFAULT)
    public Long count() {
        return tourDao.count();
    }

    public Tour save(Tour tour, File file) {
        if (tour.getId() != null) {
            Optional<Tour> tourFromDBOptional = tourDao.findById(tour.getId());
            if (tourFromDBOptional.isPresent()) {
                Tour tourFromDB = tourFromDBOptional.get();
                tourFromDB.setDate(tour.getDate());
                tourFromDB.setTitle(tour.getTitle());
                tourFromDB.setAddress(tour.getAddress());
                tourFromDB.setAmountPlayers(tour.getAmountPlayers());
                tourFromDB.setPlayer(tour.getPlayer());
                return tourDao.save(tourFromDB);
            }
        }
        return tourDao.save(tour);
    }
//    @Transactional
    public Tour save(Tour tour, MultipartFile multipartFile) {
        if (tour.getId() != null) {
            tourDao.findById(tour.getId()).ifPresent(
                    (p) -> tour.setVersion(p.getVersion())
            );
        }
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String pathToSavedFile = tourImageService.save(multipartFile);
            TourImage tourImage = TourImage.builder()
                    .path(pathToSavedFile)
                    .tour(tour)
                    .build();
            tour.addImage(tourImage);
        }
        return tourDao.save(tour);
    }

    @Transactional
    public Tour save(final Tour tour) {
        return save(tour, (MultipartFile) null);
    }


    public List<Tour> findAll() {
        List<Tour> all = tourDao.findAll();
        return all;
    }

    public void deleteById(Long id) {
        try {
            tourDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
        }
    }
    public List<PlayerTournament> findAllByTourId(Long tourId){
        return playerTournamentRepo.findAllByTournamentIdOrderByPlayerId(tourId);
    }

    public List<Player> getListPlayersForFutureTour(List<PlayerTournament> playerTournamentList){
        List<Player> playerListForFutureTour = new ArrayList<>();
        for (PlayerTournament p : playerTournamentList) {
            playerListForFutureTour.add(playerService.findById(p.getPlayerId()));
        }
        playerListForFutureTour.sort(Comparator.comparing(Player :: getRating).reversed());
        return playerListForFutureTour;
    }

    public List<Tour> findAll(int page, int size) {
        return tourDao.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size));
    }
    @Transactional(readOnly = true)
    public List<Tour> findAllActiveSortedById() {
        return tourDao.findAllByStatus(Status.ACTIVE, Sort.by(Sort.Direction.DESC,"id"));
    }
    @Transactional(readOnly = true)
    public List<Tour> findAllActiveSortedByRating() {
        return tourDao.findAllByStatus(Status.ACTIVE, Sort.by(Sort.Direction.DESC,"rating"));
    }
    @Transactional(readOnly = true)
    public List<Tour> findAllSortedById(int page, int size) {
        return tourDao.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size, Sort.by("id")));
    }
    @Transactional(readOnly = true)
    public List<Tour> findAllSortedByRating() {
        return tourDao.findAll(Sort.by(Sort.Direction.DESC,"data"));
    }

    @Transactional(readOnly = true)
    public Tour findById(Long id) {
    return tourDao.findById(id).orElse(null);
}

}
