package com.example.tt_nsk.controller;

import com.example.tt_nsk.dao.RegisteredPlayersRepo;
import com.example.tt_nsk.dao.UpcomingTournamentDataRepo;
import com.example.tt_nsk.dto.AnnouncedTournament;
import com.example.tt_nsk.entity.RegisteredPlayer;
import com.example.tt_nsk.entity.UpcomingTournamentData;
import com.example.tt_nsk.entity.security.PlayerTournament;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/tournaments/enrollment")
@Tag(name = "Контроллер, позволяющий регистрировать игроков на турниры. Версия 2")
@ConditionalOnProperty(prefix = "enrolltournament", name = "version", havingValue = "2")
public class EnrollTournamentImpl_v2 implements EnrollTournament {

    private final RegisteredPlayersRepo registeredPlayersRepo;
    private final UpcomingTournamentDataRepo upcomingTournamentDataRepo;

    @PostMapping(value = "/new_tournament")
    @Operation(summary = "Объявить регистрацию на предстоящий турнир")
    public ResponseEntity<UpcomingTournamentData> announceTournament(@RequestBody AnnouncedTournament announcedTournament) throws SQLException {
        Timestamp registrationEnds = Timestamp.valueOf(announcedTournament.getRegistrationEndsStr());
        UpcomingTournamentData upcomingTournamentData = new UpcomingTournamentData(announcedTournament.getTourId(), announcedTournament.getTotalPlayers(), registrationEnds);
        try {
            upcomingTournamentDataRepo.save(upcomingTournamentData);
        } catch (org.springframework.dao.DataIntegrityViolationException exception) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(upcomingTournamentDataRepo.findLast(), HttpStatus.CREATED);
    }

    @Override
    public String getUpcomingTournaments(HttpSession httpSession, Model model) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<UpcomingTournamentData> upcomingTournamentDataList = upcomingTournamentDataRepo.findAllByRegistrationEndsGreaterThan(now);
        return upcomingTournamentDataList.toString();
    }

    @Override
    public List<PlayerTournament> getTournamentsByPlayerId(Long playerId) {
        //ToDo
        return (List) registeredPlayersRepo.findAllByPlayerId(playerId);
    }

    @Override
    public String enrollTournament(HttpSession httpSession, Model model, Long playerId, Long tournamentId) {
        RegisteredPlayer registeredPlayer = new RegisteredPlayer(playerId, tournamentId);
        try {
            registeredPlayersRepo.save(registeredPlayer);
        }catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public String disenrollTournament(HttpSession httpSession, Model model, Long playerId, Long tournamentId) {
        registeredPlayersRepo.deleteByPlayerIdAndTourId(playerId, tournamentId);
        registeredPlayersRepo.refreshStatuses(tournamentId);
        return null;
    }
}
