package com.example.tt_nsk.controller;

import com.example.tt_nsk.dao.PlayerDao;
import com.example.tt_nsk.dao.PlayerTournamentRepo;
import com.example.tt_nsk.dao.TourDao;
import com.example.tt_nsk.dto.CurrentTournament;
import com.example.tt_nsk.dto.PlayerBriefRepresentationDto;
import com.example.tt_nsk.entity.*;
import com.example.tt_nsk.entity.enums.TourStatus;
import com.example.tt_nsk.service.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Api
@Controller
@RequiredArgsConstructor
@RequestMapping("/tour")
public class PlayController {
    private final PlayService playService;
    private final TourController tourController;
    private final PlayerService playerService;
    private final AddressService addressService;
    private final TourImageService tourImageService;
    private final TourDao tourDao;
    Map<String, Scoring> resultTour;
    List<String> list;
    String filename;
    private static final String path = "tours";
    @Value("${storage.location}")
    private String storagePath;

    private final PlayerTournamentRepo playerTournamentRepo;
    private final PlayerDao playerDao;

    private final ModelMapper modelMapper = new ModelMapper();

    public List<Player> getAllActiveSortedByRating() {
        return playerService.findAllActiveSortedByRating();
    }

    @GetMapping("/allplayers")
    @ResponseBody
    public List<PlayerBriefRepresentationDto> getAllRegisteredPlayers(Long tournamentId) {
        List<Long> playerIdList = playerTournamentRepo.findAllByTournamentIdOrderByPlayerId(tournamentId)
                .stream().map(pt -> pt.getPlayerId()).collect(Collectors.toList());
        List<Player> playerList = playerDao.findAllByIdsOrderByRatingDesc(playerIdList);
        //createCurrentTournament(playerList.stream().map(player -> modelMapper.map(player, PlayerBriefRepresentationDto.class)).collect(Collectors.toList()));
        return playerList.stream().map(player -> modelMapper.map(player, PlayerBriefRepresentationDto.class)).collect(Collectors.toList());
    }


    @GetMapping("/currentscore")
    public String createCurrentTournament(HttpSession httpSession, Model model){
        List<PlayerBriefRepresentationDto> playerBriefRepresentationDtoListSortedByRatingDesc = getAllRegisteredPlayers(87L);
        List<List<String>> results = playService.compileResultTable(playerBriefRepresentationDtoListSortedByRatingDesc);

        CurrentTournament ct = CurrentTournament.builder()
                .players(playerBriefRepresentationDtoListSortedByRatingDesc)
                .resultTable(results)
                .build();
        model.addAttribute("score", ct);

        return "tour/currentScore.html";
    }



    @PostMapping("/count")
    @ResponseBody
    public String scoringTour(Score score, Model model, HttpSession httpSession) {
        LegUp legUp = playService.getLegUp(playService.getLegUpBeforeStartingTour(playService.getCurrentRatingAllPlayers()));
        List<Player> allActiveSortedByRating = getAllActiveSortedByRating();
        switch (allActiveSortedByRating.size()) {
            case 3:
                tourController.createListPlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 4:
                tourController.createListFor4PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 5:
                tourController.createListFor5PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 6:
                tourController.createListFor6PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 7:
                tourController.createListFor7PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 8:
                tourController.createListFor8PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 9:
                tourController.createListFor9PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 10:
                tourController.createListFor10PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 11:
                tourController.createListFor11PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 12:
                tourController.createListFor12PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 13:
                tourController.createListFor13PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
        }
        list = playService.arrayWithoutNull(playService.getListResultTour(score));


        score.setEndTour((playService.getSizeArrayList(list) / allActiveSortedByRating.size() + 1) == allActiveSortedByRating.size());
//        System.out.println((playService.getSizeArrayList(list)/allActiveSortedByRating.size() + 1));
//        System.out.println((playService.getSizeArrayList(list)/allActiveSortedByRating.size() + 1) == allActiveSortedByRating.size());
//        System.out.println(score);
//        JSONObject jsonObject = new JSONObject(score);
//        System.out.println(jsonObject);
        resultTour = playService.getResultTour(list);
        playService.placePlayer(resultTour);
        model.addAttribute("legUp", legUp);
        return returnPageScoring(allActiveSortedByRating, model, resultTour);
    }

    public String returnPageScoring(List<Player> allActiveSortedByRating, Model model, Map<String, Scoring> resultTour) {
        switch (allActiveSortedByRating.size()) {
            case 3:
                tourController.addAttributeFor3Model(resultTour, model);
                return "tour/tour-form-server-for3players";
            case 4:
                tourController.addAttributeFor4Model(resultTour, model);
                return "tour/tour-form-server-for4players";
            case 5:
                tourController.addAttributeFor5Model(resultTour, model);
                return "tour/tour-form-server-for5players";
            case 6:
                tourController.addAttributeFor6Model(resultTour, model);
                return "tour/tour-form-server-for6players";
            case 7:
                tourController.addAttributeFor7Model(resultTour, model);
                return "tour/tour-form-server-for7players";
            case 8:
                tourController.addAttributeFor8Model(resultTour, model);
                return "tour/tour-form-server-for8players";
            case 9:
                tourController.addAttributeFor9Model(resultTour, model);
                return "tour/tour-form-server-for9players";
            case 10:
                tourController.addAttributeFor10Model(resultTour, model);
                return "tour/tour-form-server-for10players";
            case 11:
                tourController.addAttributeFor11Model(resultTour, model);
                return "tour/tour-form-server-for11players";
            case 12:
                tourController.addAttributeFor12Model(resultTour, model);
                return "tour/tour-form-server-for12players";
            case 13:
                tourController.addAttributeFor13Model(resultTour, model);
                return "tour/tour-form-server-for13players";
            default:
                return "tour/tour-form";
        }
    }

    @GetMapping("/save")
    @PreAuthorize("hasAnyAuthority('player.create')")
    public String showFormForPlayedTour(Model model) {
        Tour tour = new Tour();
        model.addAttribute("addressService", addressService);
        model.addAttribute("tour", tour);
        filename = createScreenshotMultipleScreens();
        return "tour/add-played-tour";
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('player.create', 'player.update') ")
    public String saveTourForPlayedTour(Tour tour, @RequestParam("files") MultipartFile[] files) {
        savePlayedTour(tour);
        savePlayersFromTour(resultTour);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(tour.getId().toString(), resultTour);
        tour.setResultTour(jsonObject.toString());
        tourDao.save(tour);
//        uploadMultipleFiles(files, playerDao.findByLastname(player.getLastname()).get().getId());
        tourController.uploadMultipleFiles(files, tourDao.findById(tour.getId()).get().getId());
        return "redirect:/tour/all";
    }


    private void savePlayersFromTour(Map<String, Scoring> resultTour) {
        List<Scoring> listFromMap = playService.getListFromMap(resultTour);
        for (Scoring sc : listFromMap) {
            Player byId = playerService.findById(sc.getIdPlayer());
            byId.setRating(BigDecimal.valueOf(sc.getRating()));
            playerService.save(byId);
        }
    }


    public Tour savePlayedTour(Tour tour, MultipartFile multipartFile) {
        tour.setAmountPlayers(BigDecimal.valueOf(resultTour.size()));
        tour.setDate(new Date());
        tour.setStatus(TourStatus.FINISHED);
        tour.setPlayer(playerService.findById(playService.getIdFirstPlace(resultTour)));
        saveScreenshot(tour);
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
    public Tour savePlayedTour(final Tour tour) {
        return savePlayedTour(tour, (MultipartFile) null);
    }

    // prt sc
    public void saveScreenshot(Tour tour) {
        TourImage tourImage = TourImage.builder()
                .path(filename + ".png")
                .tour(tour)
                .build();
        tour.addImage(tourImage);
    }

    public String createScreenshot() {
        BufferedImage image;
        String filename;
        try {
            image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        try {
            filename = String.valueOf(UUID.randomUUID());
            ImageIO.write(image, "png", new File(String.valueOf(Paths.get(storagePath).resolve(path).resolve(filename + ".png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filename;
    }

    public String createScreenshotMultipleScreens() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();
        BufferedImage image;
        String filename;
        Rectangle allScreenBounds = new Rectangle();
        for (GraphicsDevice screen : screens) {
            Rectangle screenBounds = screen.getDefaultConfiguration().getBounds();
            allScreenBounds.width += screenBounds.width;
            allScreenBounds.height = Math.max(allScreenBounds.height, screenBounds.height);
        }
        try {
            image = new Robot().createScreenCapture(new Rectangle(allScreenBounds));
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        try {
            filename = String.valueOf(UUID.randomUUID());
            ImageIO.write(image, "png", new File(String.valueOf(Paths.get(storagePath).resolve(path).resolve(filename + ".png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filename;
    }

}





