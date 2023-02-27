package site.tt_nsk.controller;

import site.tt_nsk.dao.RegisteredPlayersRepo;
import site.tt_nsk.dao.UpcomingTournamentDataRepo;
import site.tt_nsk.dto.AnnouncedTournament;
import site.tt_nsk.entity.RegisteredPlayer;
import site.tt_nsk.entity.UpcomingTournamentData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/tournaments/enrollment")
@Tag(name = "Контроллер, позволяющий регистрировать игроков на турниры. Версия 2")
@ConditionalOnProperty(prefix = "enrolltournament", name = "version", havingValue = "2")
public class EnrollTournamentImpl_v2

{

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

    @Operation(summary = "Получение списка предстоящих турниров")
    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity<List<UpcomingTournamentData>> getUpcomingTournaments() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<UpcomingTournamentData> upcomingTournamentDataList = upcomingTournamentDataRepo.findAllByRegistrationEndsIsAfter(now);

        return new ResponseEntity<>(upcomingTournamentDataList, HttpStatus.OK);
    }

    @Operation(summary = "Получение списка турниров, на которые записан игрок")
    @GetMapping(value = "/tournaments/{playerId}")
    @ResponseBody
    public ResponseEntity<List<RegisteredPlayer>> getTournamentsByPlayerId(Long playerId) {
        return new ResponseEntity<>(registeredPlayersRepo.findAllByPlayerId(playerId), HttpStatus.OK);
    }

    @Operation(summary = "Зарегистрировать игрока на турнир")
    @RequestMapping(value = "/enroll/{playerId}/{tournamentId}", method = RequestMethod.GET)
    public ResponseEntity<List<RegisteredPlayer>> enrollTournament(
            @Parameter(name = "playerId", description = "ID игрока", example = "2") @PathVariable Long playerId,
            @Parameter(name = "tournamentId", description = "ID турнира", example = "3") @PathVariable Long tournamentId
    ) {
        RegisteredPlayer registeredPlayer = new RegisteredPlayer(playerId, tournamentId, "REGISTERED");
        try {
            registeredPlayersRepo.save(registeredPlayer);
            upcomingTournamentDataRepo.findTotalPlayersByTourId(tournamentId)
                    .ifPresent(maxPlayers -> registeredPlayersRepo.refreshStatuses(tournamentId, maxPlayers));
        }catch (Exception ex) {
            System.out.println(ex);
        }
        return new ResponseEntity<>(registeredPlayersRepo.findAllByPlayerId(playerId), HttpStatus.OK);
    }

    @Operation(summary = "Снять игрока с турнира")
    @GetMapping("/disenroll/{playerId}/{tournamentId}")
    public ResponseEntity<List<RegisteredPlayer>> disenrollTournament(
            @Parameter(name = "playerId", description = "ID игрока", example = "1") @PathVariable Long playerId,
            @Parameter(name = "tournamentId", description = "ID турнира", example = "3") @PathVariable Long tournamentId
    ) {
        registeredPlayersRepo.deleteByPlayerIdAndTourId(playerId, tournamentId);
        upcomingTournamentDataRepo.findTotalPlayersByTourId(tournamentId)
                .ifPresent(maxPlayers -> registeredPlayersRepo.refreshStatuses(tournamentId, maxPlayers));
        return new ResponseEntity<>(registeredPlayersRepo.findAllByPlayerId(playerId), HttpStatus.OK);
    }

}
