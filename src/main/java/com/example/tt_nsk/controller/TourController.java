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
//    public String findAllActiveSortedRating(Model model, HttpSession httpSession) {
//        List<Player> allActiveSortedByRating = playerService.findAllActiveSortedByRating();
//        model.addAttribute("playersTour", allActiveSortedByRating);
//        httpSession.setAttribute("countPlaying", playerService.countPlaying());
//        return "tour/tour-form";
//    }
    @GetMapping
    public String findAllActiveSortedRatingForWebpage(Model model,Score score, HttpSession httpSession) {
        Tour tour = new Tour();
        List<Player> allActiveSortedByRating = playerService.findAllActiveSortedByRating();
        LegUp legUp = playService.getLegUp(playService.getLegUpBeforeStartingTour(playService.getCurrentRatingAllPlayers()));
        model.addAttribute("legUp", legUp);
        model.addAttribute("tour", tour);
        model.addAttribute("scope", score);
        httpSession.setMaxInactiveInterval(25000);

        return returnPage(allActiveSortedByRating, model, httpSession);
    }
    public String returnPage(List<Player> allActiveSortedByRating, Model model, HttpSession httpSession){
      switch (allActiveSortedByRating.size()) {
          case 3:
              createListPlayersTour(model, httpSession, allActiveSortedByRating);
              return "tour/tour-form-server-for3players";
          case 4:
              createListFor4PlayersTour(model, httpSession, allActiveSortedByRating);
              return "tour/tour-form-server-for4players";
          case 5:
              createListFor5PlayersTour(model, httpSession, allActiveSortedByRating);
              return "tour/tour-form-server-for5players";
          case 6:
              createListFor6PlayersTour(model, httpSession, allActiveSortedByRating);
              return "tour/tour-form-server-for6players";
          case 7:
              createListFor7PlayersTour(model, httpSession, allActiveSortedByRating);
              return "tour/tour-form-server-for7players";
          case 8:
              createListFor8PlayersTour(model, httpSession, allActiveSortedByRating);
              return "tour/tour-form-server-for8players";
          case 9:
              createListFor9PlayersTour(model, httpSession, allActiveSortedByRating);
              return "tour/tour-form-server-for9players";
          case 10:
              createListFor10PlayersTour(model, httpSession, allActiveSortedByRating);
              return "tour/tour-form-server-for10players";
          case 11:
              createListFor11PlayersTour(model, httpSession, allActiveSortedByRating);
              return "tour/tour-form-server-for11players";
          case 12:
              createListFor12PlayersTour(model, httpSession, allActiveSortedByRating);
              return "tour/tour-form-server-for12players";
          case 13:
              createListFor13PlayersTour(model, httpSession, allActiveSortedByRating);
              return "tour/tour-form-server-for13players";
          default:
              model.addAttribute("playersTour", allActiveSortedByRating);
              httpSession.setAttribute("countPlaying", playerService.countPlaying());
              return "tour/tour-form";
      }
    }
    public void createListPlayersTour(Model model, HttpSession httpSession, List<Player> allActiveSortedByRating){
        model.addAttribute("player1", allActiveSortedByRating.get(0));
        model.addAttribute("player2", allActiveSortedByRating.get(1));
        model.addAttribute("player3", allActiveSortedByRating.get(2));
        setHttpSession(httpSession);
        Map<String, Scoring> resultTour = playService.writeMapWithNullScore();
        addAttributeFor3Model(resultTour, model);
    }

    public void addAttributeFor3Model (Map<String, Scoring> resultTour, Model model){
        model.addAttribute("result1" , resultTour.get("0"));
        model.addAttribute("result2" , resultTour.get("1"));
        model.addAttribute("result3" , resultTour.get("2"));
    }
    public void createListFor4PlayersTour(Model model, HttpSession httpSession, List<Player> allActiveSortedByRating){
        model.addAttribute("player1", allActiveSortedByRating.get(0));
        model.addAttribute("player2", allActiveSortedByRating.get(1));
        model.addAttribute("player3", allActiveSortedByRating.get(2));
        model.addAttribute("player4", allActiveSortedByRating.get(3));
        setHttpSession(httpSession);
        Map<String, Scoring> resultTour = playService.writeMapWithNullScore();
        addAttributeFor4Model(resultTour, model);

    }
    public void addAttributeFor4Model (Map<String, Scoring> resultTour, Model model){
        model.addAttribute("result1" , resultTour.get("0"));
        model.addAttribute("result2" , resultTour.get("1"));
        model.addAttribute("result3" , resultTour.get("2"));
        model.addAttribute("result4" , resultTour.get("3"));
    }



    public void createListFor5PlayersTour(Model model, HttpSession httpSession, List<Player> allActiveSortedByRating){
        model.addAttribute("player1", allActiveSortedByRating.get(0));
        model.addAttribute("player2", allActiveSortedByRating.get(1));
        model.addAttribute("player3", allActiveSortedByRating.get(2));
        model.addAttribute("player4", allActiveSortedByRating.get(3));
        model.addAttribute("player5", allActiveSortedByRating.get(4));
        setHttpSession(httpSession);
        Map<String, Scoring> resultTour = playService.writeMapWithNullScore();
        addAttributeFor5Model(resultTour, model);

    }
    public void addAttributeFor5Model (Map<String, Scoring> resultTour, Model model){
        model.addAttribute("result1" , resultTour.get("0"));
        model.addAttribute("result2" , resultTour.get("1"));
        model.addAttribute("result3" , resultTour.get("2"));
        model.addAttribute("result4" , resultTour.get("3"));
        model.addAttribute("result5" , resultTour.get("4"));
    }

    public void createListFor6PlayersTour(Model model, HttpSession httpSession, List<Player> allActiveSortedByRating){
        model.addAttribute("player1", allActiveSortedByRating.get(0));
        model.addAttribute("player2", allActiveSortedByRating.get(1));
        model.addAttribute("player3", allActiveSortedByRating.get(2));
        model.addAttribute("player4", allActiveSortedByRating.get(3));
        model.addAttribute("player5", allActiveSortedByRating.get(4));
        model.addAttribute("player6", allActiveSortedByRating.get(5));
        setHttpSession(httpSession);
        Map<String, Scoring> resultTour = playService.writeMapWithNullScore();
        addAttributeFor6Model(resultTour, model);

    }

    public void addAttributeFor6Model (Map<String, Scoring> resultTour, Model model){
        model.addAttribute("result1" , resultTour.get("0"));
        model.addAttribute("result2" , resultTour.get("1"));
        model.addAttribute("result3" , resultTour.get("2"));
        model.addAttribute("result4" , resultTour.get("3"));
        model.addAttribute("result5" , resultTour.get("4"));
        model.addAttribute("result6" , resultTour.get("5"));
    }

    public void createListFor7PlayersTour(Model model, HttpSession httpSession, List<Player> allActiveSortedByRating){
        model.addAttribute("player1", allActiveSortedByRating.get(0));
        model.addAttribute("player2", allActiveSortedByRating.get(1));
        model.addAttribute("player3", allActiveSortedByRating.get(2));
        model.addAttribute("player4", allActiveSortedByRating.get(3));
        model.addAttribute("player5", allActiveSortedByRating.get(4));
        model.addAttribute("player6", allActiveSortedByRating.get(5));
        model.addAttribute("player7", allActiveSortedByRating.get(6));
        setHttpSession(httpSession);
        Map<String, Scoring> resultTour = playService.writeMapWithNullScore();
        addAttributeFor7Model(resultTour, model);

    }

    public void addAttributeFor7Model (Map<String, Scoring> resultTour, Model model){
        model.addAttribute("result1" , resultTour.get("0"));
        model.addAttribute("result2" , resultTour.get("1"));
        model.addAttribute("result3" , resultTour.get("2"));
        model.addAttribute("result4" , resultTour.get("3"));
        model.addAttribute("result5" , resultTour.get("4"));
        model.addAttribute("result6" , resultTour.get("5"));
        model.addAttribute("result7" , resultTour.get("6"));
    }


    public void createListFor8PlayersTour(Model model, HttpSession httpSession, List<Player> allActiveSortedByRating){
        model.addAttribute("player1", allActiveSortedByRating.get(0));
        model.addAttribute("player2", allActiveSortedByRating.get(1));
        model.addAttribute("player3", allActiveSortedByRating.get(2));
        model.addAttribute("player4", allActiveSortedByRating.get(3));
        model.addAttribute("player5", allActiveSortedByRating.get(4));
        model.addAttribute("player6", allActiveSortedByRating.get(5));
        model.addAttribute("player7", allActiveSortedByRating.get(6));
        model.addAttribute("player8", allActiveSortedByRating.get(7));
        setHttpSession(httpSession);
        Map<String, Scoring> resultTour = playService.writeMapWithNullScore();
        addAttributeFor8Model(resultTour, model);
    }
    public void addAttributeFor8Model (Map<String, Scoring> resultTour, Model model){
        model.addAttribute("result1" , resultTour.get("0"));
        model.addAttribute("result2" , resultTour.get("1"));
        model.addAttribute("result3" , resultTour.get("2"));
        model.addAttribute("result4" , resultTour.get("3"));
        model.addAttribute("result5" , resultTour.get("4"));
        model.addAttribute("result6" , resultTour.get("5"));
        model.addAttribute("result7" , resultTour.get("6"));
        model.addAttribute("result8" , resultTour.get("7"));
    }

    public void createListFor9PlayersTour(Model model, HttpSession httpSession, List<Player> allActiveSortedByRating){
        model.addAttribute("player1", allActiveSortedByRating.get(0));
        model.addAttribute("player2", allActiveSortedByRating.get(1));
        model.addAttribute("player3", allActiveSortedByRating.get(2));
        model.addAttribute("player4", allActiveSortedByRating.get(3));
        model.addAttribute("player5", allActiveSortedByRating.get(4));
        model.addAttribute("player6", allActiveSortedByRating.get(5));
        model.addAttribute("player7", allActiveSortedByRating.get(6));
        model.addAttribute("player8", allActiveSortedByRating.get(7));
        model.addAttribute("player9", allActiveSortedByRating.get(8));
        setHttpSession(httpSession);
        Map<String, Scoring> resultTour = playService.writeMapWithNullScore();
        addAttributeFor9Model(resultTour, model);
    }
    public void addAttributeFor9Model (Map<String, Scoring> resultTour, Model model){
        model.addAttribute("result1" , resultTour.get("0"));
        model.addAttribute("result2" , resultTour.get("1"));
        model.addAttribute("result3" , resultTour.get("2"));
        model.addAttribute("result4" , resultTour.get("3"));
        model.addAttribute("result5" , resultTour.get("4"));
        model.addAttribute("result6" , resultTour.get("5"));
        model.addAttribute("result7" , resultTour.get("6"));
        model.addAttribute("result8" , resultTour.get("7"));
        model.addAttribute("result9" , resultTour.get("8"));
    }

    public void createListFor10PlayersTour(Model model, HttpSession httpSession, List<Player> allActiveSortedByRating){
        model.addAttribute("player1", allActiveSortedByRating.get(0));
        model.addAttribute("player2", allActiveSortedByRating.get(1));
        model.addAttribute("player3", allActiveSortedByRating.get(2));
        model.addAttribute("player4", allActiveSortedByRating.get(3));
        model.addAttribute("player5", allActiveSortedByRating.get(4));
        model.addAttribute("player6", allActiveSortedByRating.get(5));
        model.addAttribute("player7", allActiveSortedByRating.get(6));
        model.addAttribute("player8", allActiveSortedByRating.get(7));
        model.addAttribute("player9", allActiveSortedByRating.get(8));
        model.addAttribute("player10", allActiveSortedByRating.get(9));
        setHttpSession(httpSession);
        Map<String, Scoring> resultTour = playService.writeMapWithNullScore();
        addAttributeFor10Model(resultTour, model);
    }
    public void addAttributeFor10Model (Map<String, Scoring> resultTour, Model model){
        model.addAttribute("result1" , resultTour.get("0"));
        model.addAttribute("result2" , resultTour.get("1"));
        model.addAttribute("result3" , resultTour.get("2"));
        model.addAttribute("result4" , resultTour.get("3"));
        model.addAttribute("result5" , resultTour.get("4"));
        model.addAttribute("result6" , resultTour.get("5"));
        model.addAttribute("result7" , resultTour.get("6"));
        model.addAttribute("result8" , resultTour.get("7"));
        model.addAttribute("result9" , resultTour.get("8"));
        model.addAttribute("result10" , resultTour.get("9"));
    }

    public void createListFor11PlayersTour(Model model, HttpSession httpSession, List<Player> allActiveSortedByRating){
        model.addAttribute("player1", allActiveSortedByRating.get(0));
        model.addAttribute("player2", allActiveSortedByRating.get(1));
        model.addAttribute("player3", allActiveSortedByRating.get(2));
        model.addAttribute("player4", allActiveSortedByRating.get(3));
        model.addAttribute("player5", allActiveSortedByRating.get(4));
        model.addAttribute("player6", allActiveSortedByRating.get(5));
        model.addAttribute("player7", allActiveSortedByRating.get(6));
        model.addAttribute("player8", allActiveSortedByRating.get(7));
        model.addAttribute("player9", allActiveSortedByRating.get(8));
        model.addAttribute("player10", allActiveSortedByRating.get(9));
        model.addAttribute("player11", allActiveSortedByRating.get(10));
        setHttpSession(httpSession);
        Map<String, Scoring> resultTour = playService.writeMapWithNullScore();
        addAttributeFor11Model(resultTour, model);

    }

    public void addAttributeFor11Model (Map<String, Scoring> resultTour, Model model){
        model.addAttribute("result1" , resultTour.get("0"));
        model.addAttribute("result2" , resultTour.get("1"));
        model.addAttribute("result3" , resultTour.get("2"));
        model.addAttribute("result4" , resultTour.get("3"));
        model.addAttribute("result5" , resultTour.get("4"));
        model.addAttribute("result6" , resultTour.get("5"));
        model.addAttribute("result7" , resultTour.get("6"));
        model.addAttribute("result8" , resultTour.get("7"));
        model.addAttribute("result9" , resultTour.get("8"));
        model.addAttribute("result10" , resultTour.get("9"));
        model.addAttribute("result11" , resultTour.get("10"));
    }

    public void createListFor12PlayersTour(Model model, HttpSession httpSession, List<Player> allActiveSortedByRating){
        model.addAttribute("player1", allActiveSortedByRating.get(0));
        model.addAttribute("player2", allActiveSortedByRating.get(1));
        model.addAttribute("player3", allActiveSortedByRating.get(2));
        model.addAttribute("player4", allActiveSortedByRating.get(3));
        model.addAttribute("player5", allActiveSortedByRating.get(4));
        model.addAttribute("player6", allActiveSortedByRating.get(5));
        model.addAttribute("player7", allActiveSortedByRating.get(6));
        model.addAttribute("player8", allActiveSortedByRating.get(7));
        model.addAttribute("player9", allActiveSortedByRating.get(8));
        model.addAttribute("player10", allActiveSortedByRating.get(9));
        model.addAttribute("player11", allActiveSortedByRating.get(10));
        model.addAttribute("player12", allActiveSortedByRating.get(11));
//        model.addAttribute("player13", allActiveSortedByRating.get(12));
        setHttpSession(httpSession);
        Map<String, Scoring> resultTour = playService.writeMapWithNullScore();
        addAttributeFor12Model(resultTour, model);

    }

    public void addAttributeFor12Model (Map<String, Scoring> resultTour, Model model){
        model.addAttribute("result1" , resultTour.get("0"));
        model.addAttribute("result2" , resultTour.get("1"));
        model.addAttribute("result3" , resultTour.get("2"));
        model.addAttribute("result4" , resultTour.get("3"));
        model.addAttribute("result5" , resultTour.get("4"));
        model.addAttribute("result6" , resultTour.get("5"));
        model.addAttribute("result7" , resultTour.get("6"));
        model.addAttribute("result8" , resultTour.get("7"));
        model.addAttribute("result9" , resultTour.get("8"));
        model.addAttribute("result10" , resultTour.get("9"));
        model.addAttribute("result11" , resultTour.get("10"));
        model.addAttribute("result12" , resultTour.get("11"));
//        model.addAttribute("result13" , resultTour.get("12"));
    }
    public void createListFor13PlayersTour(Model model, HttpSession httpSession, List<Player> allActiveSortedByRating){
        model.addAttribute("player1", allActiveSortedByRating.get(0));
        model.addAttribute("player2", allActiveSortedByRating.get(1));
        model.addAttribute("player3", allActiveSortedByRating.get(2));
        model.addAttribute("player4", allActiveSortedByRating.get(3));
        model.addAttribute("player5", allActiveSortedByRating.get(4));
        model.addAttribute("player6", allActiveSortedByRating.get(5));
        model.addAttribute("player7", allActiveSortedByRating.get(6));
        model.addAttribute("player8", allActiveSortedByRating.get(7));
        model.addAttribute("player9", allActiveSortedByRating.get(8));
        model.addAttribute("player10", allActiveSortedByRating.get(9));
        model.addAttribute("player11", allActiveSortedByRating.get(10));
        model.addAttribute("player12", allActiveSortedByRating.get(11));
        model.addAttribute("player13", allActiveSortedByRating.get(12));
        setHttpSession(httpSession);
        Map<String, Scoring> resultTour = playService.writeMapWithNullScore();
        addAttributeFor13Model(resultTour, model);

    }
    public void addAttributeFor13Model (Map<String, Scoring> resultTour, Model model){
        model.addAttribute("result1" , resultTour.get("0"));
        model.addAttribute("result2" , resultTour.get("1"));
        model.addAttribute("result3" , resultTour.get("2"));
        model.addAttribute("result4" , resultTour.get("3"));
        model.addAttribute("result5" , resultTour.get("4"));
        model.addAttribute("result6" , resultTour.get("5"));
        model.addAttribute("result7" , resultTour.get("6"));
        model.addAttribute("result8" , resultTour.get("7"));
        model.addAttribute("result9" , resultTour.get("8"));
        model.addAttribute("result10" , resultTour.get("9"));
        model.addAttribute("result11" , resultTour.get("10"));
        model.addAttribute("result12" , resultTour.get("11"));
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

//    @GetMapping("/participate")
//    public String participate(Model model) {
////        model.addAttribute("tours", playerService.findAllSortedByRating());
////        List<Long> imagesId = new ArrayList<>(playerImageService.uploadMultipleFiles(id));
////        model.addAttribute("playerImagesId", imagesId);
//        return "tour/tour-list";
//    }

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
        return returnPage(allActiveSortedByRating, model, httpSession);
    }

//    public Player getPlayer(Principal principal){
//        Player player;
//        AccountUser accountUser = changeRole(principal);
//        return player = accountUser.getPlayer();
//    }

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
//        model.addAttribute("categoryService", categoryService);
        return "tour/tour-info";
    }
    @PostMapping("/new")
    @PreAuthorize("hasAnyAuthority('player.create', 'player.update') ")
    public String saveTour(Tour tour, @RequestParam("files") MultipartFile[] files) {
        tourService.save(tour);
//        uploadMultipleFiles(files, playerDao.findByLastname(player.getLastname()).get().getId());
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




