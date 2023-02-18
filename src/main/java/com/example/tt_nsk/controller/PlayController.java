package com.example.tt_nsk.controller;

import com.example.tt_nsk.dao.PlayerDao;
import com.example.tt_nsk.dao.PlayerTournamentRepo;
import com.example.tt_nsk.dao.TourDao;
import com.example.tt_nsk.entity.*;
import com.example.tt_nsk.entity.enums.TourStatus;
import com.example.tt_nsk.service.AddressService;
import com.example.tt_nsk.service.PlayService;
import com.example.tt_nsk.service.PlayerService;
import com.example.tt_nsk.service.TourImageService;
import com.example.tt_nsk.tournament.CurrentTournament;
import com.example.tt_nsk.tournament.TournamentData;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Api
@Controller
@RequiredArgsConstructor
@RequestMapping("/tour")
@Tag(name = "Контроллер, позволяющий отслеживать ход игры и сохранять результаты")
public class PlayController {
    private final PlayService playService;
    private final TourController tourController;
    public final PlayerService playerService;
    private final AddressService addressService;
    private final TourImageService tourImageService;
    private final TourDao tourDao;
    Map<String, Scoring> resultTour;
    List<String> list;
    String filename;
    private static final String path = "tours";

    @Value("${storage.location}")
    private String storagePath;

    public final PlayerTournamentRepo playerTournamentRepo;
    public final PlayerDao playerDao;


    public List<Player> getAllActiveSortedByRating() {
        return playerService.findAllActiveSortedByRating();
    }

    @Operation(summary = "Начало турнира")
    @GetMapping("/new_tournament/{tourId}/{setsToWinGame}")
    @ResponseBody
    public ResponseEntity<TournamentData> startTournament(HttpSession httpSession, Model model,
                                                          @Parameter(name = "tourId", description = "ID турнира", example = "87") @PathVariable long tourId,
                                                          @Parameter(name = "setsToWinGame", description = "Количество выигранных сетов для победы в игре", example = "3") @PathVariable int setsToWinGame) {
        TournamentData tournamentData = null;
        if (!CurrentTournament.getInstance().hasTourStarted()) {
            tournamentData = playService.startTournament(tourId, setsToWinGame);
            model.addAttribute("tournament", tournamentData);
        }
        //return "tour/setting-score.html";
        return new ResponseEntity<>(tournamentData, HttpStatus.OK);
    }

    @Operation(summary = "Восстановление сохраненного турнира из базы данных")
    @GetMapping("/restored_tournament/{tourId}")
    @ResponseBody
    public ResponseEntity<TournamentData> restoreTournament(HttpSession httpSession, Model model,
                                                            @Parameter(name = "tourId", description = "ID турнира", example = "87") @PathVariable long tourId
    ) {
        Optional<TournamentData> tournamentDataOptional = playService.restoreTournament(tourId);
        if (tournamentDataOptional.isPresent()) {
            CurrentTournament currentTournament = CurrentTournament.getInstance();
            currentTournament.startTour(tournamentDataOptional.get(), tournamentDataOptional.get().getPlaySetsToWinGame());
            return new ResponseEntity<>(tournamentDataOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    @Operation(summary = "Получение текущего счета")
    @GetMapping("/currentscore")
    @ResponseBody
    public ResponseEntity<List<List<TournamentData.PlaySet>>> currentScore(HttpSession httpSession, Model model) {

        if (CurrentTournament.getInstance().hasTourStarted()) {
            model.addAttribute("tournament", CurrentTournament.getInstance().tournamentData());
            //return "tour/setting-score.html";
            List<List<TournamentData.PlaySet>> currentScore = CurrentTournament.getInstance().tournamentData().getGamesList().stream().map(game -> game.getPlaySetList()).collect(Collectors.toList());
            return new ResponseEntity<>(currentScore, HttpStatus.OK);
        } else {
            //return "tour/not_started_yet.html";
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Operation(summary = "Сохранение результатов сета")
    @RequestMapping(value = "/setscore/{gameOrder}/{firstPlayerResult}/{secondPlayerResult}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<TournamentData> setScore(HttpSession httpSession, Model model,
                                                   @Parameter(name = "gameOrder", description = "Номер пары игроков в турнире", example = "1") @PathVariable int gameOrder,
                                                   @Parameter(name = "firstPlayerResult", description = "Результат первого игрока в сете", example = "9") @PathVariable int firstPlayerResult,
                                                   @Parameter(name = "secondPlayerResult", description = "Результат второго игрока в сете", example = "11") @PathVariable int secondPlayerResult) {
        if (CurrentTournament.getInstance().hasTourStarted()) {
            boolean playSetAdded = CurrentTournament.getInstance().tournamentData().getGamesList().get(gameOrder).addPlaySet(firstPlayerResult, secondPlayerResult);
            model.addAttribute("tournament", CurrentTournament.getInstance().tournamentData());
            if (playSetAdded) {
                String currentTournamentState = playService.createCurrentTournamentState(CurrentTournament.getInstance().tournamentData());
            }
            return new ResponseEntity<>(CurrentTournament.getInstance().tournamentData(), HttpStatus.OK);
            //return "tour/setting-score.html";
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            //return "tour/not_started_yet.html";
        }
    }

    @PostMapping("/count")
//    @ResponseBody
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





