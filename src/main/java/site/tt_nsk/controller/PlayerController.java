package site.tt_nsk.controller;

import site.tt_nsk.dao.PlayerDao;
import site.tt_nsk.entity.Pair;
import site.tt_nsk.entity.Player;
import site.tt_nsk.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {
    private final PlayerService playerService;
    private final PlayerDao playerDao;
    private final PairService pairService;
    private final PlayerImageService playerImageService;
    private final UpdateRatingTtw updateRatingTtw;

    @GetMapping("/all")
    public String getPlayerList(Model model, HttpSession httpSession){
//        updateRatingTtw.parseRatingWithTTW();
//        updateRatingTtw.parseRating();
        httpSession.setAttribute("count", playerService.count().toString());
        httpSession.setAttribute("countPlaying", playerService.countPlaying());
        model.addAttribute("players", playerService.addListForMainPage());
        return "player/players-list";
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('player.create', 'player.update', 'player.read')")
    public String showForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        Player player;
        if (id != null) {
            player = playerService.findById(id);
        } else {
            player = new Player();
        }
        List<Long> imagesId = new ArrayList<>(playerImageService.uploadMultipleFiles(id));
        model.addAttribute("playerImagesId", imagesId);
        model.addAttribute("player", player);
        return "player/player-form";
    }

    @GetMapping("/{playerId}")
    @PreAuthorize("hasAnyAuthority('player.read') || isAnonymous()")
    public String showInfo(Model model, @PathVariable(name = "playerId") Long id) {
        Player player;
        if (id != null) {
            player = playerService.findById(id);
        } else {
            return "redirect:/player/all";
        }
        List<Long> imagesId = new ArrayList<>(playerImageService.uploadMultipleFiles(id));
        model.addAttribute("playerImagesId", imagesId);
        model.addAttribute("player", player);
        return "player/player-info";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('player.create', 'player.update', 'player.read')")
    public String savePlayer(@Valid Player player, @RequestParam("files") MultipartFile[] files, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "player/player-form";
        }
        playerService.save(player);
        uploadMultipleFiles(files, playerDao.findById(player.getId()).get().getId());
        return "redirect:/player/all";
    }
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('player.delete')")
    public String deleteById(@PathVariable(name = "id") Long id) {
        playerService.deleteById(id);
        return "redirect:/player/all";
    }

    @GetMapping("/status_delete/{id}")
    @PreAuthorize("hasAnyAuthority('player.delete')")
    public String statusDeleteById(@PathVariable(name = "id") Long id) {
        playerService.statusDelete(id);
        return "redirect:/player/all";
    }

    @GetMapping("/image_delete/{id}")
    @PreAuthorize("!isAnonymous()")
    public String imageDeleteById(@PathVariable(name = "id") Long id, Model model) {
        Long playerIdByImageId = playerImageService.getPlayerIdByImageId(id);
        Player player = playerService.findById(playerIdByImageId);
        model.addAttribute("player", player);
        log.info(playerImageService.count().toString());
        playerImageService.deleteImage(id);
        if (playerImageService.countImagesOfPlayer(id) == 0){
            playerImageService.addStartImage(player);
        }
        model.addAttribute("playerImagesId", playerImageService.uploadMultipleFiles(playerIdByImageId));
        return "player/player-form";
    }

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('player.read') || isAnonymous()")
    public byte[] getImage(@PathVariable Long id) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(playerImageService.loadFileAsImage(id), "png", byteArrayOutputStream);
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
            ImageIO.write(playerImageService.loadFileAsImageByIdImage(id), "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new byte[]{};
    }

    public void uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, Long id) {
        Arrays.stream(files)
                .map(file -> playerImageService.savePlayerImage(id, file))
                .collect(Collectors.toList());
    }

    public List<Pair> getQueuePlayers(){
        List<Pair> listOrderGames = pairService.getListOrderGames((ArrayList<Player>) playerService.findAllActiveSortedByRating());
        for (Pair p : listOrderGames) {
            log.info(p.getPlayer1().getLastname() + ":"  + p.getPlayer2().getLastname());
        }
        return listOrderGames;
    }

    public void getQueue(){
        List<Pair> listOrderGames = pairService.getListOrderGames((ArrayList<Player>) playerService.findAllActiveSortedByRating());
        for (Pair p : listOrderGames) {
            log.info(p.getPlayer1().getLastname() + ":"  + p.getPlayer2().getLastname());
        }

    }
}




