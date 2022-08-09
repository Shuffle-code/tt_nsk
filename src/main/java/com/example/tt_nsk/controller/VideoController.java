package com.example.tt_nsk.controller;

import com.example.tt_nsk.dao.PlayerDao;
import com.example.tt_nsk.service.PlayerImageService;
import com.example.tt_nsk.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/video")
public class VideoController {


    @GetMapping
    public String rules() {
        return "video/video";
    }
    
}




