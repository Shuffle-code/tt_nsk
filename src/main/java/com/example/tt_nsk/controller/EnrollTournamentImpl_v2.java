package com.example.tt_nsk.controller;

import com.example.tt_nsk.dao.RegisteredPlayersRepo;
import com.example.tt_nsk.dao.UpcomingTournamentDataRepo;
import com.example.tt_nsk.entity.security.PlayerTournament;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/tournaments/enrollment")
@Tag(name = "Контроллер, позволяющий регистрировать игроков на турниры. Версия 2")
@ConditionalOnProperty(prefix = "enrolltournament", name = "version", havingValue = "2")
public class EnrollTournamentImpl_v2 implements EnrollTournament{

    private final RegisteredPlayersRepo registeredPlayersRepo;
    private final UpcomingTournamentDataRepo upcomingTournamentDataRepo;

    @Override
    public String getUpcomingTournaments(HttpSession httpSession, Model model) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        upcomingTournamentDataRepo.findAllByRegistrationEndsGreaterThan(now);

        return null;
    }

    @Override
    public List<PlayerTournament> getTournamentsByPlayerId(Long playerId) {
        return null;
    }

    @Override
    public String enrollTournament(HttpSession httpSession, Model model, Long playerId, Long tournamentId) {
        return null;
    }

    @Override
    public String disenrollTournament(HttpSession httpSession, Model model, Long playerId, Long tournamentId) {
        return null;
    }
}
