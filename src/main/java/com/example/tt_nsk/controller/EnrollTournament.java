package com.example.tt_nsk.controller;

import com.example.tt_nsk.dao.PlayerTournamentRepo;
import com.example.tt_nsk.dao.TourDao;
import com.example.tt_nsk.dto.TournamentBriefRepresentationDto;
import com.example.tt_nsk.entity.Tour;
import com.example.tt_nsk.entity.security.AccountUser;
import com.example.tt_nsk.entity.security.PlayerTournament;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.*;

@Api
@Controller
@AllArgsConstructor
@RequestMapping("/upcomingTournaments")
@Tag(name = "Контроллер, позволяющий регистрировать игроков на турниры")
public class EnrollTournament {

    private final TourDao tourDao;
    private final PlayerTournamentRepo playerTournamentRepo;
    private final ModelMapper modelMapper;

    @Operation(summary = "Получение списка предстоящих турниров")
    @GetMapping(value = "/all")
    public String getUpcomingTournaments(HttpSession httpSession, Model model) {
        model = createModel(httpSession, model);
        return "/tour/upcoming-tours.html";

    }

    @Operation(summary = "Получение списка турниров, на которые записан игрок")
    @GetMapping(value = "/tournaments/{playerId}")
    @ResponseBody
    public Iterable<PlayerTournament> getTournamentsByPlayerId(
            @Parameter(name = "playerId", description = "ID игрока", example = "1") @PathVariable(name = "playerId") Long playerId) {
        return playerTournamentRepo.findAllByPlayerId(playerId);

    }

    @Operation(summary = "Зарегистрировать игрока на турнир")
    @RequestMapping(path = "/enroll/{playerId}/{tournamentId}", method = RequestMethod.GET)
    //@ResponseBody
    public String enrollTournament(HttpSession httpSession, Model model,
            @Parameter(name = "playerId", description = "ID игрока", example = "2") @PathVariable Long playerId,
            @Parameter(name = "tournamentId", description = "ID турнира", example = "3") @PathVariable Long tournamentId

    ) {
        PlayerTournament playerTournament = new PlayerTournament(playerId, tournamentId);
        try {
            playerTournamentRepo.save(playerTournament);
        } catch (org.springframework.dao.DataIntegrityViolationException exception) {
            return new ResponseEntity<>(playerTournamentRepo.findAllByPlayerId(playerId), HttpStatus.UNPROCESSABLE_ENTITY).toString();
        }
        model = createModel(httpSession, model);
        return "/tour/upcoming-tours.html";

    }

    @Operation(summary = "Снять игрока с турнира")
    @GetMapping(path = "/disenroll/{playerId}/{tournamentId}")
    //@ResponseBody
    public String disenrollTournament(HttpSession httpSession, Model model,
            @Parameter(name = "playerId", description = "ID игрока", example = "1") @PathVariable Long playerId,
            @Parameter(name = "tournamentId", description = "ID турнира", example = "3") @PathVariable Long tournamentId

    ) {
        playerTournamentRepo.disenroll(playerId, tournamentId);
        List<TournamentBriefRepresentationDto> tournamentBriefRepresentationDtoList = createTournamentBriefRepresentationDtoList();
        model = createModel(httpSession, model);
        return "/tour/upcoming-tours.html";

    }

    private Model createModel(HttpSession httpSession, Model model) {
        List<TournamentBriefRepresentationDto> tournamentBriefRepresentationDtoList = createTournamentBriefRepresentationDtoList();
        Optional.ofNullable((AccountUser) httpSession.getAttribute("user"))
                .ifPresent(accountUser -> {
                    model.addAttribute("playerId", accountUser.getId());
                });
        model.addAttribute("tours", tournamentBriefRepresentationDtoList);
        return model;
    }

    private List<TournamentBriefRepresentationDto> createTournamentBriefRepresentationDtoList() {
        Date date = new Date(System.currentTimeMillis());
        List<Tour> upcomingTours = tourDao.findUpcomingTournaments(date);
        List<TournamentBriefRepresentationDto> tournamentBriefRepresentationDtoList = new ArrayList<>();
        upcomingTours.forEach(tour -> {
            tournamentBriefRepresentationDtoList.add(modelMapper.map(tour, TournamentBriefRepresentationDto.class));
        });

        return tournamentBriefRepresentationDtoList;
    }


}
