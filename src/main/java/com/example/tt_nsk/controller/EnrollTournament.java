package com.example.tt_nsk.controller;

import com.example.tt_nsk.dao.PlayerTournamentRepo;
import com.example.tt_nsk.dao.TourDao;
import com.example.tt_nsk.dto.TournamentBriefRepresentationDto;
import com.example.tt_nsk.entity.Tour;
import com.example.tt_nsk.entity.security.PlayerTournament;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/enroll")
@Tag(name = "Контроллер, позволяющий регистрировать игроков на турниры")
public class EnrollTournament {

    private final TourDao tourDao;
    private final PlayerTournamentRepo playerTournamentRepo;
    private final ModelMapper modelMapper;

    @Operation(summary = "Получение списка предстоящих турниров")
    @GetMapping(value = "/upcomingTournaments")
    public List<TournamentBriefRepresentationDto> getUpcomingTournaments() {
        Date date = new Date(System.currentTimeMillis());
        List<Tour> upcomingTours = tourDao.findUpcomingTournaments(date);
        List<TournamentBriefRepresentationDto> tournamentBriefRepresentationDtoList = new ArrayList<>();
        upcomingTours.forEach(tour -> {
            tournamentBriefRepresentationDtoList.add(modelMapper.map(tour, TournamentBriefRepresentationDto.class));
        });
        return tournamentBriefRepresentationDtoList;

    }

    @Operation(summary = "Получение списка турниров, на которые записан игрок")
    @GetMapping(value = "/tournaments/{playerId}")
    public Iterable<PlayerTournament> getTournamentsByPlayerId(
            @Parameter(name = "playerId", description = "ID игрока", example = "1") Long playerId) {
        return playerTournamentRepo.findAllByPlayerId(playerId);

    }

    @Operation(summary = "Зарегистрировать игрока на турнир")
    @PutMapping(value = "/tournaments/{playerId}/{tournamentId}")
    public Iterable<PlayerTournament> enrollTournament(
            @Parameter(name = "playerId", description = "ID игрока", example = "1") Long playerId,
            @Parameter(name = "tournamentId", description = "ID турнира", example = "89") Long tournamentId)
    {
        return playerTournamentRepo.findAll();

    }



}
