package com.example.tt_nsk.controller;

import com.example.tt_nsk.dao.PlayerDao;
import com.example.tt_nsk.dao.TourDao;
import com.example.tt_nsk.entity.Player;
import com.example.tt_nsk.entity.Tour;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tour")
public class TourController {

    private final PlayerService playerService;
//    private final PlayerDao playerDao;
    private final TourDao tourDao;
    private final AddressService addressService;
    private final TourService tourService;
    private final TourImageService tourImageService;
//    private final PlayerImageService playerImageService;

    @GetMapping
    public String findAllActiveSortedRating(Model model, HttpSession httpSession) {
        Tour tour = new Tour();
        model.addAttribute("playersTour", playerService.findAllActiveSortedByRating());
        model.addAttribute("tour", tour);
        return "tour/tour-form";
    }

    @GetMapping("/all")
    public String getTourList(Model model) {
        model.addAttribute("tours", tourService.findAll());
        return "tour/tour-list";
    }

    @GetMapping("/participate")
    public String participate(Model model) {
//        model.addAttribute("tours", playerService.findAllSortedByRating());
//        List<Long> imagesId = new ArrayList<>(playerImageService.uploadMultipleFiles(id));
//        model.addAttribute("playerImagesId", imagesId);
        return "tour/tour-list";
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




