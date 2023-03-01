package site.tt_nsk.controller;

import site.tt_nsk.service.PlayService;
import site.tt_nsk.service.RefereeService;
import site.tt_nsk.tournament.CurrentTournament;
import site.tt_nsk.tournament.TournamentData;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Api
@Controller
@RequiredArgsConstructor
@RequestMapping("/referee")
@Tag(name = "Контроллер, обеспечивающий работу судьи турнира")
public class RefereeController {

    private final PlayService playService;
    private final RefereeService refereeService;

    @Operation(summary = "Начало турнира")
    @GetMapping("/new_tournament/{tourId}/{setsToWinGame}")
    @ResponseBody
    public ResponseEntity<TournamentData> startTournament(HttpSession httpSession, Model model,
                                                          @Parameter(name = "tourId", description = "ID турнира", example = "87") @PathVariable long tourId,
                                                          @Parameter(name = "setsToWinGame", description = "Количество выигранных сетов для победы в игре", example = "3") @PathVariable int setsToWinGame) {
        TournamentData tournamentData = null;
        if (!CurrentTournament.getInstance().hasTourStarted()) {
            tournamentData = refereeService.startTournament(tourId, setsToWinGame);
            model.addAttribute("tournament", tournamentData);
        }
        //return "tour/setting-score.html";
        return new ResponseEntity<>(tournamentData, HttpStatus.OK);
    }

    @Operation(summary = "Восстановление сохраненного турнира из базы данных")
    @GetMapping("/restored_tournament/{tourId}")
    @ResponseBody
    public ResponseEntity<TournamentData> restoreTournament(HttpSession httpSession, Model model,
                                                            @Parameter(name = "tourId", description = "ID турнира", example = "87") @PathVariable long tourId
    ) {
        Optional<TournamentData> tournamentDataOptional = playService.restoreTournament(tourId);
        if (tournamentDataOptional.isPresent()) {
            CurrentTournament currentTournament = CurrentTournament.getInstance();
            currentTournament.startTour(tournamentDataOptional.get(), tournamentDataOptional.get().getPlaySetsToWinGame());
            return new ResponseEntity<>(tournamentDataOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @Operation(summary = "Сохранение результатов сета")
    @RequestMapping(value = "/setscore/{gameOrder}/{firstPlayerResult}/{secondPlayerResult}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<TournamentData> setScore(HttpSession httpSession, Model model,
                                                   @Parameter(name = "gameOrder", description = "Номер пары игроков в турнире", example = "1") @PathVariable int gameOrder,
                                                   @Parameter(name = "firstPlayerResult", description = "Результат первого игрока в сете", example = "9") @PathVariable int firstPlayerResult,
                                                   @Parameter(name = "secondPlayerResult", description = "Результат второго игрока в сете", example = "11") @PathVariable int secondPlayerResult) {
        if (CurrentTournament.getInstance().hasTourStarted()) {
            CurrentTournament.getInstance().tournamentData().getGamesList().get(gameOrder).addPlaySet(firstPlayerResult, secondPlayerResult);
            model.addAttribute("tournament", CurrentTournament.getInstance().tournamentData());
            return new ResponseEntity<>(CurrentTournament.getInstance().tournamentData(), HttpStatus.OK);
            //return "tour/setting-score.html";
        }
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }



}
