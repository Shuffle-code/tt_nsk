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
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tour")
public class TourController {

    private final PlayerService playerService;
    private final PlayerDao playerDao;
    private final PlayerImageService playerImageService;

    @GetMapping
    public String findAllActiveSortedRating(Model model, HttpSession httpSession) {
        model.addAttribute("playersTour", playerService.findAllActiveSortedByRating());

//        System.out.println(playerService.findAllActiveSortedById());
        return "tour/tour-form";
    }

    @GetMapping("/all")
    public String getTourList(Model model) {
        model.addAttribute("tours", playerService.findAllSortedByRating());
        return "tour/tour-list";
    }

    @GetMapping("/participate")
    public String participate(Model model) {
        model.addAttribute("tours", playerService.findAllSortedByRating());
//        List<Long> imagesId = new ArrayList<>(playerImageService.uploadMultipleFiles(id));
//        model.addAttribute("playerImagesId", imagesId);
        return "tour/tour-list";
    }



}




