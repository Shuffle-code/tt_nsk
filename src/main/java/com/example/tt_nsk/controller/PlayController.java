package com.example.tt_nsk.controller;
import com.example.tt_nsk.entity.Score;
import com.example.tt_nsk.entity.Scoring;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tour")
public class PlayController {


    @GetMapping("/count")
//    @PreAuthorize("hasAnyAuthority('player.create', 'player.update') ")
    public String saveTour(Score score, Model model, HttpSession httpSession) {
        Scoring scoring = new Scoring();
//        tourService.save(score); // TODO: 02.12.2022
        model.addAttribute("scope", scoring);
        return "tour/tour-form-server";
    }
}



