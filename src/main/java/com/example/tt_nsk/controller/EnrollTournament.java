package com.example.tt_nsk.controller;

import com.example.tt_nsk.entity.security.PlayerTournament;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface EnrollTournament {
    @Operation(summary = "Получение списка предстоящих турниров")
    @GetMapping(value = "/all")
    String getUpcomingTournaments(HttpSession httpSession, Model model);

    @Operation(summary = "Получение списка турниров, на которые записан игрок")
    @GetMapping(value = "/tournaments/{playerId}")
    @ResponseBody
    List<PlayerTournament> getTournamentsByPlayerId(
            @Parameter(name = "playerId", description = "ID игрока", example = "1") @PathVariable(name = "playerId") Long playerId);

    @Operation(summary = "Зарегистрировать игрока на турнир")
    @RequestMapping(value = "/enroll/{playerId}/{tournamentId}", method = RequestMethod.GET)
    String enrollTournament(HttpSession httpSession, Model model,
                            @Parameter(name = "playerId", description = "ID игрока", example = "2") @PathVariable Long playerId,
                            @Parameter(name = "tournamentId", description = "ID турнира", example = "3") @PathVariable Long tournamentId

    );

    @Operation(summary = "Снять игрока с турнира")
    @GetMapping("/disenroll/{playerId}/{tournamentId}")
    String disenrollTournament(HttpSession httpSession, Model model,
                               @Parameter(name = "playerId", description = "ID игрока", example = "1") @PathVariable Long playerId,
                               @Parameter(name = "tournamentId", description = "ID турнира", example = "3") @PathVariable Long tournamentId

    );
}
