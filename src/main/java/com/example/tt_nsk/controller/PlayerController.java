package com.example.tt_nsk.controller;

import com.example.tt_nsk.dao.PlayerDao;
import com.example.tt_nsk.entity.Player;
import com.example.tt_nsk.service.PlayerImageService;
import com.example.tt_nsk.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerDao playerDao;
    private final PlayerImageService playerImageService;

    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PreAuthorize("hasAnyAuthority('Player.read')")
    public String getPlayerList(Model model) {
        model.addAttribute("players", playerService.findAll());
        return "player/players-list";
    }

//    @GetMapping
//    @PreAuthorize("hasAnyAuthority('player.create', 'player.update')")
//    public String showForm(Model model, @RequestParam(name = "id", required = false) Long id) {
//        PlayerDto playerDto;
//        if (id != null) {
//            playerDto = playerService.findById(id);
////            List<String> images = new ArrayList<>(PlayerImageService.uploadMultipleFilesByPlayerId(id));
////            model.addAttribute("PlayerImages", images);
//        } else {
//            playerDto = new PlayerDto();
//        }
////        model.addAttribute("categoryService", categoryService);
////        model.addAttribute("manufacturers", manufacturerService.findAll());
//        model.addAttribute("Player", playerDto);
//        return "player/player-form";
//    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('player.create', 'player.update')")
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

//    @GetMapping("/{PlayerId}")
//    @PreAuthorize("hasAnyAuthority('Player.read')")
//    public String showInfo(Model model, @PathVariable(name = "PlayerId") Long id) {
//        PlayerDto playerDto;
//        if (id != null) {
//            playerDto = playerService.findById(id);
//        } else {
//            return "redirect:/Player/all";
//        }
////        List<Long> imagesId = new ArrayList<>(PlayerImageService.uploadMultipleFiles(id));
////        model.addAttribute("PlayerImagesId", imagesId);
//        model.addAttribute("player", playerDto);
////        model.addAttribute("categoryService", categoryService);
//        return "player/player-info";
//    }
    @GetMapping("/{playerId}")
    @PreAuthorize("hasAnyAuthority('player.read')")
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
    @PostMapping
    @PreAuthorize("hasAnyAuthority('player.create', 'player.update')")
//    public String savePlayer(PlayerDto PlayerDto) {
//        PlayerDto.setManufactureDate(LocalDate.now()); // todo
//        PlayerService.save(PlayerDto);
//    public String savePlayer(Player player, @RequestParam("file") MultipartFile file) {
    public String savePlayer(Player player, @RequestParam("file") MultipartFile file, @RequestParam("files") MultipartFile[] files) {
//        Player.setManufactureDate(LocalDate.now());
//    public String savePlayer(Player player, @RequestParam("file") MultipartFile file) {

        // todo
//        playerService.save(player, file);
        playerService.save(player, file);
        System.out.println(file);
//        playerDao.findByLastname(player.getLastname()).get().getId();
        if (files.length != 0){
            System.out.println(files);
            uploadMultipleFiles(files, playerDao.findByLastname(player.getLastname()).get().getId());
        }

        return "redirect:/player/all";
    }
//
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('player.delete')")
    public String deleteById(@PathVariable(name = "id") Long id) {
        playerService.deleteById(id);
        return "redirect:/player/all";
    }
    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(playerImageService.loadFileAsImage(id), "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

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




