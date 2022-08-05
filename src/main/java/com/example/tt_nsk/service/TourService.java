package com.example.tt_nsk.service;

import com.example.tt_nsk.dao.PlayerDao;
import com.example.tt_nsk.dao.TourDao;
import com.example.tt_nsk.entity.Player;
import com.example.tt_nsk.entity.PlayerImage;
import com.example.tt_nsk.entity.Tour;
import com.example.tt_nsk.entity.TourImage;
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
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TourService {
    private final TourDao tourDao;
    private final TourImageService tourImageService;

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
                tourFromDB.setAddressId(tour.getAddressId());
                tourFromDB.setAmountPlayers(tour.getAmountPlayers());
//                tourFromDB.setWinnerId(tour.getWinnerId());
                return tourDao.save(tourFromDB);
            }
        }
        return tourDao.save(tour);
    }
//    @Transactional
    public Tour save(Tour tour, MultipartFile multipartFile) {
//        Product product = productMapper.toProduct(productDto, manufacturerDao, categoryDao);
//        Product productFromDB = productDao.getById(productDto.getId()); // todo при создании нового продукта id=null
//        if (productFromDB != null && !productDto.getCost().equals(productFromDB.getCost())) sendMessage(productDto);
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
        return tourDao.findAll();
    }


    public void deleteById(Long id) {
        try {
            tourDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
        }
    }

//    public List<Tour> findAll(int page, int size) {
//        return tourDao.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size));
//    }
//    @Transactional(readOnly = true)
//    public List<Tour> findAllActiveSortedById() {
//        return tourDao.findAllByStatus(Status.ACTIVE, Sort.by(Sort.Direction.DESC,"id"));
//    }
//    @Transactional(readOnly = true)
//    public List<Tour> findAllActiveSortedByRating() {
//        return tourDao.findAllByStatus(Status.ACTIVE, Sort.by(Sort.Direction.DESC,"rating"));
//    }
//    @Transactional(readOnly = true)
//    public List<Tour> findAllSortedById(int page, int size) {
//        return tourDao.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size, Sort.by("id")));
//    }
    @Transactional(readOnly = true)
    public List<Tour> findAllSortedByRating() {
        return tourDao.findAll(Sort.by(Sort.Direction.DESC,"data"));
    }

//    @Transactional(readOnly = true)
//    public PlayerDto findById(Long id) {
//        return playerMapper.toPlayerDto(playerDao.findById(id).orElse(null));
//    }
    @Transactional(readOnly = true)
    public Tour findById(Long id) {
    return tourDao.findById(id).orElse(null);
}
}
