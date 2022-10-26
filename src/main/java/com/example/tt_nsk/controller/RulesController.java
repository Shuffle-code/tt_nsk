package com.example.tt_nsk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rules")
public class RulesController {

    @GetMapping
    public String rules() {
        return "rules/rules";
    }
    
}




