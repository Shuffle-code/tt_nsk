package com.example.tt_nsk.controller;

import com.example.tt_nsk.dao.PlayerDao;
import com.example.tt_nsk.entity.Player;
import com.example.tt_nsk.service.PlayerImageService;
import com.example.tt_nsk.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
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
    private final PlayerImageService playerImageService;



    @GetMapping("/all")
    public String getPlayerList(Model model, HttpSession httpSession) {
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
//            List<String> images = new ArrayList<>(PlayerImageService.uploadMultipleFilesByPlayerId(id));
//            model.addAttribute("PlayerImages", images);
        } else {
            player = new Player();
        }
//        model.addAttribute("categoryService", categoryService);
//        model.addAttribute("manufacturers", manufacturerService.findAll());
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
//        model.addAttribute("categoryService", categoryService);
        return "player/player-info";
    }

//
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
//
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
}




