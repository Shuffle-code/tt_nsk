package com.example.tt_nsk.controller;

import com.example.tt_nsk.dao.PlayerTournamentRepo;
import com.example.tt_nsk.dao.TourDao;
import com.example.tt_nsk.dto.TournamentBriefRepresentationDto;
import com.example.tt_nsk.entity.Tour;
import com.example.tt_nsk.entity.security.AccountUser;
import com.example.tt_nsk.entity.security.PlayerTournament;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<PlayerTournament> getTournamentsByPlayerId(
            @Parameter(name = "playerId", description = "ID игрока", example = "1") @PathVariable(name = "playerId") Long playerId) {
        return (List<PlayerTournament>) playerTournamentRepo.findAllByPlayerId(playerId);

    }

    @Operation(summary = "Зарегистрировать игрока на турнир")
    @RequestMapping(value = "/enroll/{playerId}/{tournamentId}", method = RequestMethod.GET)
    public String enrollTournament(HttpSession httpSession, Model model,
                                   @Parameter(name = "playerId", description = "ID игрока", example = "2") @PathVariable Long playerId,
                                   @Parameter(name = "tournamentId", description = "ID турнира", example = "3") @PathVariable Long tournamentId

    ) {
        PlayerTournament playerTournament = new PlayerTournament(playerId, tournamentId);
        try {
            playerTournamentRepo.save(playerTournament);
        } catch (org.springframework.dao.DataIntegrityViolationException exception) {
            model = createModel(httpSession, model);
            return "/tour/upcoming-tours.html";
        }
        model = createModel(httpSession, model);
        return "/tour/upcoming-tours.html";

    }

    @Operation(summary = "Снять игрока с турнира")
    @GetMapping("/disenroll/{playerId}/{tournamentId}")
    public String disenrollTournament(HttpSession httpSession, Model model,
                                      @Parameter(name = "playerId", description = "ID игрока", example = "1") @PathVariable Long playerId,
                                      @Parameter(name = "tournamentId", description = "ID турнира", example = "3") @PathVariable Long tournamentId

    ) {
        playerTournamentRepo.disenroll(playerId, tournamentId);
        model = createModel(httpSession, model);
        return "/tour/upcoming-tours.html";

    }

    private Model createModel(HttpSession httpSession, Model model) {
        Optional<AccountUser> accountUserOptional = Optional.ofNullable((AccountUser) httpSession.getAttribute("user"));
        if (accountUserOptional.isEmpty()) {
            model.addAttribute("tours", createTournamentBriefRepresentationDtoList());
        }
        accountUserOptional.ifPresent(accountUser -> {
            model.addAttribute("playerId", accountUser.getId());
            model.addAttribute("tours", createTournamentBriefRepresentationDtoList(accountUser.getId()));
        });
        return model;
    }

    private List<TournamentBriefRepresentationDto> createTournamentBriefRepresentationDtoList(long playerId) {
        Date date = new Date(System.currentTimeMillis());
        List<Tour> upcomingTours = tourDao.findUpcomingTournaments(date);
        List<TournamentBriefRepresentationDto> tournamentBriefRepresentationDtoList = new ArrayList<>();
        List<Long> registeredTournaments = compileTournamentRegistration(playerId);
        upcomingTours.forEach(tour -> {
            TournamentBriefRepresentationDto tournamentBriefRepresentationDto = modelMapper.map(tour, TournamentBriefRepresentationDto.class);
            if (registeredTournaments.contains(tournamentBriefRepresentationDto.getId())) {
                tournamentBriefRepresentationDto.setRegistered(true);
            }
            tournamentBriefRepresentationDtoList.add(tournamentBriefRepresentationDto);

        });

        return tournamentBriefRepresentationDtoList;
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

    private List<Long> compileTournamentRegistration(Long playerId) {
        return getTournamentsByPlayerId(playerId).stream()
                .map(playerTournament -> playerTournament.getTournamentId()).collect(Collectors.toList());

    }
}

