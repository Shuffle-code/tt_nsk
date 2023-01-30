package com.example.tt_nsk.controller;

import com.example.tt_nsk.dao.TourDao;
import com.example.tt_nsk.dto.UpcomingTournamentDto;
import com.example.tt_nsk.entity.Tour;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/enroll")
public class EnrollTournament {

    @Autowired
    TourDao tourDao;

    ModelMapper modelMapper = new ModelMapper();

    @Operation(summary = "Получение списка предстоящих турниров")
    @GetMapping(value = "/upcomingTournaments")
    public List<UpcomingTournamentDto> getUpcomingTournaments() {
        Date date = new Date(System.currentTimeMillis());
        List<Tour> upcomingTours = tourDao.findUpcomingTournaments(date);
        List<UpcomingTournamentDto> upcomingTournamentDtoList = new ArrayList<>();
        upcomingTours.forEach(tour -> {
            upcomingTournamentDtoList.add(modelMapper.map(tour, UpcomingTournamentDto.class));
        });
        return upcomingTournamentDtoList;

    }




}
