package com.example.tt_nsk.controller;
import com.example.tt_nsk.dao.TourDao;
import com.example.tt_nsk.dao.security.AccountRoleDao;
import com.example.tt_nsk.entity.*;
import com.example.tt_nsk.entity.enums.Status;
import com.example.tt_nsk.entity.security.AccountRole;
import com.example.tt_nsk.entity.security.AccountUser;
import com.example.tt_nsk.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tour")
public class TourController {
    private final PlayerService playerService;
    private final TourDao tourDao;
    private final UserService userService;
    private final AddressService addressService;
    private final TourService tourService;
    private final TourImageService tourImageService;
    private final AccountRoleDao accountRoleDao;
    private final PlayService playService;
    private final PairService pairService;
    @GetMapping
    public String findAllActiveSortedRatingForWebpage(Model model,Score score, HttpSession httpSession,
                                                      @RequestParam(name = "id", required = false) Long id) {
        LegUp legUp;
        List<Player> allByRating;
        Tour tour;
        if (id != null) {
            tour = tourDao.findById(id).get();
            allByRating = tourService.getListPlayersForFutureTour(tourService.findAllByTourId(id));
            legUp = playService.getLegUp(playService.getLegUpBeforeStartingTour(playService.getCurrentRatingAllPlayers(allByRating), allByRating));
        } else {
            tour = new Tour();
            allByRating = playerService.findAllActiveSortedByRating();
            legUp = playService.getLegUp(playService.getLegUpBeforeStartingTour(playService.getCurrentRatingAllPlayers()));
        }
        model.addAttribute("legUp", legUp);
        model.addAttribute("tour", tour);
        model.addAttribute("scope", score);
        httpSession.setMaxInactiveInterval(25000);
        Map<String, Scoring> resultTour = playService.writeMapWithNullScore(allByRating);
        return returnPage(allByRating, model, httpSession, resultTour);
    }
    public String returnPage(List<Player> allSortedByRating, Model model, HttpSession httpSession, Map<String, Scoring> resultTour){
      switch (allSortedByRating.size()) {
          case 3:
              createListPlayersTour(model, httpSession, allSortedByRating, resultTour);
              return "tour/tour-form-server-for3players";
          case 4:
              createListFor4PlayersTour(model, httpSession, allSortedByRating, resultTour);
              return "tour/tour-form-server-for4players";
          case 5:
              createListFor5PlayersTour(model, httpSession, allSortedByRating, resultTour);
              return "tour/tour-form-server-for5players";
          case 6:
              createListFor6PlayersTour(model, httpSession, allSortedByRating, resultTour);
              return "tour/tour-form-server-for6players";
          case 7:
              createListFor7PlayersTour(model, httpSession, allSortedByRating, resultTour);
              return "tour/tour-form-server-for7players";
          case 8:
              createListFor8PlayersTour(model, httpSession, allSortedByRating, resultTour);
              return "tour/tour-form-server-for8players";
          case 9:
              createListFor9PlayersTour(model, httpSession, allSortedByRating, resultTour);
              return "tour/tour-form-server-for9players";
          case 10:
              createListFor10PlayersTour(model, httpSession, allSortedByRating, resultTour);
              return "tour/tour-form-server-for10players";
          case 11:
              createListFor11PlayersTour(model, httpSession, allSortedByRating, resultTour);
              return "tour/tour-form-server-for11players";
          case 12:
              createListFor12PlayersTour(model, httpSession, allSortedByRating, resultTour);
              return "tour/tour-form-server-for12players";
          case 13:
              createListFor13PlayersTour(model, httpSession, allSortedByRating, resultTour);
              return "tour/tour-form-server-for13players";
          default:
              model.addAttribute("playersTour", allSortedByRating);
              httpSession.setAttribute("countPlaying", playerService.countPlaying());
              return "tour/tour-form";
      }
    }
    public void getListPairsPlayersTour(Model model, HttpSession httpSession, List<Player> allSortedByRating){
        ArrayList<Player> sortedByRating = new ArrayList(allSortedByRating);
        model.addAttribute("pairs", pairService.getListOrderGames(sortedByRating));
        setHttpSession(httpSession);
    }

    public Model createModelWith3Players (Model model, List<Player> allSortedByRating){
        model.addAttribute("player1", allSortedByRating.get(0));
        model.addAttribute("player2", allSortedByRating.get(1));
        model.addAttribute("player3", allSortedByRating.get(2));
    return model;
    }
    public Model createModelWith7Players(Model model, List<Player> allSortedByRating){
        model.addAttribute("player1", allSortedByRating.get(0));
        model.addAttribute("player2", allSortedByRating.get(1));
        model.addAttribute("player3", allSortedByRating.get(2));
        model.addAttribute("player4", allSortedByRating.get(3));
        model.addAttribute("player5", allSortedByRating.get(4));
        model.addAttribute("player6", allSortedByRating.get(5));
        model.addAttribute("player7", allSortedByRating.get(6));
        return model;
    }
    public Model createModelWith10Players(Model model, List<Player> allSortedByRating){
        model.addAttribute("player1", allSortedByRating.get(0));
        model.addAttribute("player2", allSortedByRating.get(1));
        model.addAttribute("player3", allSortedByRating.get(2));
        model.addAttribute("player4", allSortedByRating.get(3));
        model.addAttribute("player5", allSortedByRating.get(4));
        model.addAttribute("player6", allSortedByRating.get(5));
        model.addAttribute("player7", allSortedByRating.get(6));
        model.addAttribute("player8", allSortedByRating.get(7));
        model.addAttribute("player9", allSortedByRating.get(8));
        model.addAttribute("player10", allSortedByRating.get(9));
        return model;
    }

    public Model addAttributeFor3Model (Map<String, Scoring> resultTour, Model model){
        model.addAttribute("result1" , resultTour.get("0"));
        model.addAttribute("result2" , resultTour.get("1"));
        model.addAttribute("result3" , resultTour.get("2"));
        return model;
    }
    public void createListPlayersTour(Model model, HttpSession httpSession, List<Player> allSortedByRating, Map<String, Scoring> resultTour){
        createModelWith3Players(model, allSortedByRating);
        getListPairsPlayersTour(model, httpSession, allSortedByRating);
        addAttributeFor3Model(resultTour, model);
    }
    public void createListFor4PlayersTour(Model model, HttpSession httpSession, List<Player> allSortedByRating, Map<String, Scoring> resultTour){
        createModelWith3Players(model, allSortedByRating);
        model.addAttribute("player4", allSortedByRating.get(3));
        getListPairsPlayersTour(model, httpSession, allSortedByRating);
        addAttributeFor4Model(resultTour, model);
    }
    public Model addAttributeFor4Model (Map<String, Scoring> resultTour, Model model){
        addAttributeFor3Model(resultTour, model);
        model.addAttribute("result4" , resultTour.get("3"));
        return model;
    }

    public void createListFor5PlayersTour(Model model, HttpSession httpSession, List<Player> allSortedByRating, Map<String, Scoring> resultTour){
        createModelWith3Players(model, allSortedByRating);
        model.addAttribute("player4", allSortedByRating.get(3));
        model.addAttribute("player5", allSortedByRating.get(4));
        getListPairsPlayersTour(model, httpSession, allSortedByRating);
        addAttributeFor5Model(resultTour, model);

    }
    public Model addAttributeFor5Model (Map<String, Scoring> resultTour, Model model){
        addAttributeFor4Model(resultTour,model);
        model.addAttribute("result5" , resultTour.get("4"));
        return model;
    }

    public void createListFor6PlayersTour(Model model, HttpSession httpSession, List<Player> allSortedByRating, Map<String, Scoring> resultTour){
        createModelWith3Players(model, allSortedByRating);
        model.addAttribute("player4", allSortedByRating.get(3));
        model.addAttribute("player5", allSortedByRating.get(4));
        model.addAttribute("player6", allSortedByRating.get(5));
        getListPairsPlayersTour(model, httpSession, allSortedByRating);
        addAttributeFor6Model(resultTour, model);
    }

    public Model addAttributeFor6Model (Map<String, Scoring> resultTour, Model model){
        addAttributeFor5Model(resultTour, model);
        model.addAttribute("result6" , resultTour.get("5"));
        return model;
    }

    public void createListFor7PlayersTour(Model model, HttpSession httpSession, List<Player> allSortedByRating, Map<String, Scoring> resultTour){
        createModelWith7Players(model, allSortedByRating);
        getListPairsPlayersTour(model, httpSession, allSortedByRating);
        addAttributeFor7Model(resultTour, model);

    }

    public Model addAttributeFor7Model (Map<String, Scoring> resultTour, Model model){
        addAttributeFor6Model(resultTour, model);
        model.addAttribute("result7" , resultTour.get("6"));
        return model;
    }


    public void createListFor8PlayersTour(Model model, HttpSession httpSession, List<Player> allSortedByRating, Map<String, Scoring> resultTour){
        createModelWith7Players(model, allSortedByRating);
        model.addAttribute("player8", allSortedByRating.get(7));
        getListPairsPlayersTour(model, httpSession, allSortedByRating);
        addAttributeFor8Model(resultTour, model);
    }
    public Model addAttributeFor8Model (Map<String, Scoring> resultTour, Model model){
        addAttributeFor7Model(resultTour, model);
        model.addAttribute("result8" , resultTour.get("7"));
        return model;
    }

    public void createListFor9PlayersTour(Model model, HttpSession httpSession, List<Player> allSortedByRating, Map<String, Scoring> resultTour){
        createModelWith7Players(model, allSortedByRating);
        model.addAttribute("player8", allSortedByRating.get(7));
        model.addAttribute("player9", allSortedByRating.get(8));
        getListPairsPlayersTour(model, httpSession, allSortedByRating);
        addAttributeFor9Model(resultTour, model);
    }
    public Model addAttributeFor9Model (Map<String, Scoring> resultTour, Model model){
        addAttributeFor8Model(resultTour, model);
        model.addAttribute("result9" , resultTour.get("8"));
        return model;
    }

    public void createListFor10PlayersTour(Model model, HttpSession httpSession, List<Player> allSortedByRating, Map<String, Scoring> resultTour){
        createModelWith10Players(model, allSortedByRating);
        getListPairsPlayersTour(model, httpSession, allSortedByRating);
        addAttributeFor10Model(resultTour, model);
    }
    public Model addAttributeFor10Model (Map<String, Scoring> resultTour, Model model){
        addAttributeFor9Model(resultTour, model);
        model.addAttribute("result10" , resultTour.get("9"));
        return model;
    }

    public void createListFor11PlayersTour(Model model, HttpSession httpSession, List<Player> allSortedByRating, Map<String, Scoring> resultTour){
        createModelWith10Players(model, allSortedByRating);
        model.addAttribute("player11", allSortedByRating.get(10));
        getListPairsPlayersTour(model, httpSession, allSortedByRating);
        addAttributeFor11Model(resultTour, model);

    }

    public Model addAttributeFor11Model (Map<String, Scoring> resultTour, Model model){
        addAttributeFor10Model(resultTour, model);
        model.addAttribute("result11" , resultTour.get("10"));
        return model;
    }

    public void createListFor12PlayersTour(Model model, HttpSession httpSession, List<Player> allSortedByRating, Map<String, Scoring> resultTour){
        createModelWith10Players(model, allSortedByRating);
        model.addAttribute("player11", allSortedByRating.get(10));
        model.addAttribute("player12", allSortedByRating.get(11));
        getListPairsPlayersTour(model, httpSession, allSortedByRating);
        addAttributeFor12Model(resultTour, model);

    }

    public Model addAttributeFor12Model (Map<String, Scoring> resultTour, Model model){
        addAttributeFor11Model(resultTour, model);
        model.addAttribute("result12" , resultTour.get("11"));
        return model;
    }
    public void createListFor13PlayersTour(Model model, HttpSession httpSession, List<Player> allSortedByRating, Map<String, Scoring> resultTour){
        createModelWith10Players(model, allSortedByRating);
        model.addAttribute("player11", allSortedByRating.get(10));
        model.addAttribute("player12", allSortedByRating.get(11));
        model.addAttribute("player13", allSortedByRating.get(12));
        getListPairsPlayersTour(model, httpSession, allSortedByRating);
        addAttributeFor13Model(resultTour, model);

    }
    public void addAttributeFor13Model (Map<String, Scoring> resultTour, Model model){
        addAttributeFor12Model(resultTour, model);
        model.addAttribute("result13" , resultTour.get("12"));
    }
    public void setHttpSession (HttpSession httpSession){
        httpSession.setAttribute("countPlaying", playerService.countPlaying());
    }
    @GetMapping("/all")
    public String getTourList(Model model) {
        model.addAttribute("tours", tourService.findAll());
        return "tour/tour-list";
    }
    @GetMapping("/participate")
    public String changeStatus(Model model, Score score, Principal principal, HttpSession httpSession){
        Tour tour = new Tour();
        Player player;
        AccountUser accountUser = changeRole(principal);
        player = accountUser.getPlayer();
        player.setStatus(Status.ACTIVE);
        playerService.save(player);
        List<Player> allActiveSortedByRating = playerService.findAllActiveSortedByRating();
        LegUp legUp = playService.getLegUp(playService.getLegUpBeforeStartingTour(playService.getCurrentRatingAllPlayers(allActiveSortedByRating)));
        model.addAttribute("playersTour", allActiveSortedByRating);
        model.addAttribute("scope", score);
        httpSession.setAttribute("countPlaying", playerService.countPlaying());
        model.addAttribute("legUp", legUp);
//        httpSession.setAttribute("role", accountUser.getRoles());
        model.addAttribute("tour", tour);
        Map<String, Scoring> resultTour = playService.writeMapWithNullScore(allActiveSortedByRating);
        return returnPage(allActiveSortedByRating, model, httpSession, resultTour);
    }
    @GetMapping("/noParticipate")
    public String changeStatusBack(Model model, Principal principal, HttpSession httpSession) {
        Player player;
        Tour tour = new Tour();
        AccountUser accountUser = changeRoleBack(principal);
        player = accountUser.getPlayer();
        player.setStatus(Status.DISABLE);
        playerService.save(player);
        model.addAttribute("playersTour", playerService.findAllActiveSortedByRating());
        httpSession.setAttribute("countPlaying", playerService.countPlaying());
        model.addAttribute("tour", tour);

        return "redirect:/player/all";
    }

    public AccountUser changeRoleBack(Principal principal){
        AccountRole roleUser = accountRoleDao.findByName("ROLE_USER");
        AccountUser accountUser = userService.findByUsername(principal.getName());
        Integer userId = accountRoleDao.findRoleIdByUserId(accountUser.getId());
        if (!(userId == 1l)) {
            accountUser.setRoles(Set.of(roleUser));
        }
        return accountUser;
    }


    public AccountUser changeRole(Principal principal){
        AccountRole rolePlayer = accountRoleDao.findByName("ROLE_PLAYER");
        AccountUser accountUser = userService.findByUsername(principal.getName());
        Integer userId = accountRoleDao.findRoleIdByUserId(accountUser.getId());
        if (!(userId == 1l)){
            accountUser.setRoles(Set.of(rolePlayer));
        }
        return accountUser;
    }
    @GetMapping("/new")
    @PreAuthorize("hasAnyAuthority('player.create', 'player.update')")
    public String showForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        Tour tour;
        if (id != null) {
            tour = tourService.findById(id);
//            List<String> images = new ArrayList<>(PlayerImageService.uploadMultipleFilesByPlayerId(id));
//            model.addAttribute("PlayerImages", images);
        } else {
            tour = new Tour();
        }
        model.addAttribute("players", playerService.findAll());
        model.addAttribute("addressService", addressService);
        model.addAttribute("tour", tour);
        return "tour/tour-add";
    }
    @GetMapping("/{tourId}")
    @PreAuthorize("hasAnyAuthority('player.read') || isAnonymous()")
    public String showInfo(Model model, @PathVariable(name = "tourId") Long id) {
        Tour tour;
        if (id != null) {
            tour = tourService.findById(id);
        } else {
            return "redirect:/tour/all";
        }
        List<Long> imagesId = new ArrayList<>(tourImageService.uploadMultipleFiles(id));
        model.addAttribute("tourImagesId", imagesId);
        model.addAttribute("tour", tour);
        return "tour/tour-info";
    }
    @PostMapping("/new")
    @PreAuthorize("hasAnyAuthority('player.create', 'player.update') ")
    public String saveTour(Tour tour, @RequestParam("files") MultipartFile[] files) {
        tourService.save(tour);
        uploadMultipleFiles(files, tourDao.findById(tour.getId()).get().getId());
        return "redirect:/tour/all";
    }

    public void uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, Long id) {
        Arrays.stream(files)
                .map(file -> tourImageService.saveTourImage(id, file))
                .collect(Collectors.toList());
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('player.delete')")
    public String deleteById(@PathVariable(name = "id") Long id) {
        tourService.deleteById(id);
        return "redirect:/tour/all";
    }

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('player.read') || isAnonymous()")
    public byte[] getImage(@PathVariable Long id) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(tourImageService.loadFileAsImage(id), "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }
    @PreAuthorize("hasAnyAuthority('player.read') || isAnonymous()")
    @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getAllImage(@PathVariable Long id) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(tourImageService.loadFileAsImageByIdImage(id), "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }
}




