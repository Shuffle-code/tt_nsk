package site.tt_nsk.controller;

import site.tt_nsk.dao.PlayerDao;
import site.tt_nsk.dao.PlayerTournamentRepo;
import site.tt_nsk.dao.TourDao;
import site.tt_nsk.dto.CurrentTournament;
import site.tt_nsk.dto.PlayerBriefRepresentationDto;
//import com.example.tt_nsk.entity.*;
import site.tt_nsk.entity.enums.TourStatus;
//import com.example.tt_nsk.service.*;
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
import site.tt_nsk.entity.*;
import site.tt_nsk.service.*;


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
    String scoring;
    List<String> list;
    String filename;
    private static final String path = "tours";
    @Value("${storage.location}")
    private String storagePath;
    private final PlayerTournamentRepo playerTournamentRepo;
    private final PlayerDao playerDao;
    private final TourService tourService;
    private final ModelMapper modelMapper = new ModelMapper();

    public List<Player> getAllActiveSortedByRating(){
        return playerService.findAllActiveSortedByRating();
    }
    public List<Player> getAllSortedByRating(@PathVariable(name = "tourId") Long id){
        return tourService.getListPlayersForFutureTour(tourService.findAllByTourId(id));
    }
    @GetMapping("/all-players")
    @ResponseBody
    public List<PlayerBriefRepresentationDto> getAllRegisteredPlayers(Long tournamentId) {
        List<Long> playerIdList = playerTournamentRepo.findAllByTournamentIdOrderByPlayerId(tournamentId)
                .stream().map(pt -> pt.getPlayerId()).collect(Collectors.toList());
        List<Player> playerList = playerDao.findAllByIdsOrderByRatingDesc(playerIdList);
        //createCurrentTournament(playerList.stream().map(player -> modelMapper.map(player, PlayerBriefRepresentationDto.class)).collect(Collectors.toList()));
        return playerList.stream().map(player -> modelMapper.map(player, PlayerBriefRepresentationDto.class)).collect(Collectors.toList());
    }
    @GetMapping("/current-score")
    public String createCurrentTournament(HttpSession httpSession, Model model){
        List<PlayerBriefRepresentationDto> playerBriefRepresentationDtoListSortedByRatingDesc = getAllRegisteredPlayers(91L);
        List<List<String>> results = playService.compileResultTable(playerBriefRepresentationDtoListSortedByRatingDesc);
        CurrentTournament ct = CurrentTournament.builder()
                .players(playerBriefRepresentationDtoListSortedByRatingDesc)
                .resultTable(results)
                .build();
        model.addAttribute("score", ct);
        return "tour/currentScore";
    }
    @PostMapping("/count")
    public String scoringTour(Score score, Model model, HttpSession httpSession,
                              @RequestParam(name = "id", required = false) Long id) {
        LegUp legUp;
        List<Player> allByRating;
        Tour tour;
        if (id != null) {
            tour = tourDao.findById(id).get();
            allByRating = tourService.getListPlayersForFutureTour(tourService.findAllByTourId(id));
            legUp = playService.getLegUp(playService.getLegUpBeforeStartingTour(playService.getCurrentRatingAllPlayers(allByRating), allByRating));
            list = playService.arrayWithoutNull(playService.getListResultTour(score));
            score.setEndTour((playService.getSizeArrayList(list)/allByRating.size() + 1) == allByRating.size());
            resultTour = playService.getResultTour(list, allByRating);
            playService.placePlayer(resultTour);
            scoring = playService.arrayWithoutNull(playService.getListResultTour(score)).toString();
            tourDao.updateTour(playService.arrayWithoutNull(playService.getListResultTour(score)).toString(), id);
        } else {
            tour = new Tour();
            allByRating = playerService.findAllActiveSortedByRating();
            legUp = playService.getLegUp(playService.getLegUpBeforeStartingTour(playService.getCurrentRatingAllPlayers()));
            list = playService.arrayWithoutNull(playService.getListResultTour(score));
            score.setEndTour((playService.getSizeArrayList(list)/allByRating.size() + 1) == allByRating.size());
            resultTour = playService.getResultTour(list);
            playService.placePlayer(resultTour);
            scoring = playService.arrayWithoutNull(playService.getListResultTour(score)).toString();
        }
        model.addAttribute("legUp", legUp);
        model.addAttribute("tour", tour);
        return tourController.returnPage(allByRating, model, httpSession, resultTour);
    }
    @GetMapping("/save")
    @PreAuthorize("hasAnyAuthority('player.create')")
    public String showFormForPlayedTour(Model model,
            @RequestParam(name = "id", required = false) Long id) {
        Tour tour;
        if (id != null) {
            tour = tourDao.findById(id).get();
        }else {
            tour = new Tour();
        }
        model.addAttribute("addressService", addressService);
        model.addAttribute("tour", tour);
        filename = createScreenshotMultipleScreens();
        return "tour/add-played-tour";
    }
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('player.create', 'player.update') ")
    public String saveTourForPlayedTour(Tour tour, @RequestParam("files") MultipartFile[] files) {
        JSONObject jsonObjectResult = new JSONObject();
        if (tour.getId() == null) {
            savePlayedTour(tour);
            savePlayersFromTour(resultTour);
            jsonObjectResult.put(tour.getId().toString(), resultTour);
            tour.setResultTour(jsonObjectResult.toString());
            tour.setStatus(TourStatus.FINISHED);
            tour.setScoring(scoring);
            tourDao.save(tour);
            tourController.uploadMultipleFiles(files, tourDao.findById(tour.getId()).get().getId());
        }else {
            savePlayersFromTour(resultTour);
            jsonObjectResult.put(tour.getId().toString(), resultTour);
            tourDao.updateTourAfterSave(scoring, jsonObjectResult.toString(), TourStatus.FINISHED.toString(),
                playerService.findById(playService.getIdFirstPlace(resultTour)).getId(), tour.getId());}
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

    public HashMap<String, String> getLegUpBeforeStartingTour(List<Double> currentRating){
        HashMap<String, String> legUpStrArr = new HashMap<>();
        Double currentRatingElement = getAllActiveSortedByRating().get(0).getRating().doubleValue();
        for (int i = 0; i < currentRating.size() - 1 ; i++) {
            for (int j = 1; j < currentRating.size(); j++) {
                if (currentRatingElement > currentRating.get(j) && currentRatingElement != 500) {
                    legUpStrArr.put("fx" + (i + 1) + "y" + (j + 1), scoringLegUp(currentRatingElement, currentRating.get(j)));
                }
            }
            currentRatingElement = currentRating.get(i + 1);
        }
        return legUpStrArr;
    }

    public String scoringLegUp (Double ratingPlayerHighRating, Double ratingPlayerLowRating){
        double difference = ratingPlayerHighRating - ratingPlayerLowRating;
        if (difference >= 0 && difference <= 25){
            return "0/0";
        } else if (difference > 25 && difference <= 50){
            return "0/1";
        } else if (difference > 50 && difference <= 75){
            return "0/2";
        } else if (difference > 75 && difference <= 100){
            return "0/3";
        } else if (difference > 100 && difference <= 125){
            return "0/4";
        } else if (difference > 125 && difference  <= 150){
            return "0/5";
        } else if (difference > 150 && difference <= 175) {
            return "0/6";
        }
        return "0/7";
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





