package site.tt_nsk.controller;
import site.tt_nsk.dao.PlayerTournamentRepo;
import site.tt_nsk.dao.TourDao;
import site.tt_nsk.dto.TournamentBriefRepresentationDto;
import site.tt_nsk.entity.Tour;
import site.tt_nsk.entity.security.AccountUser;
import site.tt_nsk.entity.security.PlayerTournament;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Controller
@AllArgsConstructor
@RequestMapping("/upcomingTournaments")
@ConditionalOnProperty(prefix = "enrolltournament", name = "version", havingValue = "1")
@Tag(name = "Контроллер, позволяющий регистрировать игроков на турниры. Версия 1")
public class EnrollTournamentImpl_v1 implements EnrollTournament {
    private final TourDao tourDao;
    private final PlayerTournamentRepo playerTournamentRepo;
    private final ModelMapper modelMapper;
    @Override
    public String getUpcomingTournaments(HttpSession httpSession, Model model) {
        model = createModel(httpSession, model);
        return "tour/upcoming-tours";
    }

    @Override
    public List<PlayerTournament> getTournamentsByPlayerId(
            @Parameter(name = "playerId", description = "ID игрока", example = "1") @PathVariable(name = "playerId") Long playerId) {
        return (List<PlayerTournament>) playerTournamentRepo.findAllByPlayerId(playerId);
    }

    @Override
    public String enrollTournament(HttpSession httpSession, Model model,
                                   @Parameter(name = "playerId", description = "ID игрока", example = "2") @PathVariable Long playerId,
                                   @Parameter(name = "tournamentId", description = "ID турнира", example = "3") @PathVariable Long tournamentId

    ) {
        PlayerTournament playerTournament = new PlayerTournament(playerId, tournamentId);

        try {
            playerTournamentRepo.save(playerTournament);
            int size = playerTournamentRepo.findAllByTournamentIdOrderByPlayerId(tournamentId).size();
            Tour tour = tourDao.findById(tournamentId).get();
            tour.setAmountPlayers(BigDecimal.valueOf(size));
            tourDao.save(tour);
        } catch (org.springframework.dao.DataIntegrityViolationException exception) {
            model = createModel(httpSession, model);
            return "tour/upcoming-tours";
        }
        model = createModel(httpSession, model);
        return "tour/upcoming-tours";
    }

    @Override
    public String disenrollTournament(HttpSession httpSession, Model model,
                                      @Parameter(name = "playerId", description = "ID игрока", example = "1") @PathVariable Long playerId,
                                      @Parameter(name = "tournamentId", description = "ID турнира", example = "3") @PathVariable Long tournamentId
    ) {
        playerTournamentRepo.disenroll(playerId, tournamentId);
        int size = playerTournamentRepo.findAllByTournamentIdOrderByPlayerId(tournamentId).size();
        Tour tour = tourDao.findById(tournamentId).get();
        tour.setAmountPlayers(BigDecimal.valueOf(size));
        tourDao.save(tour);
        model = createModel(httpSession, model);
        return "tour/upcoming-tours";
    }

    private Model createModel(HttpSession httpSession, Model model) {
        Optional<AccountUser> accountUserOptional = Optional.ofNullable((AccountUser) httpSession.getAttribute("user"));
        if (accountUserOptional.isEmpty()) {
            model.addAttribute("tours", createTournamentBriefRepresentationDtoList());
        }
        accountUserOptional.ifPresent(accountUser -> {
            model.addAttribute("playerId", accountUser.getPlayer().getId());
            model.addAttribute("tours", createTournamentBriefRepresentationDtoList(accountUser.getPlayer().getId()));
        });
        return model;
    }

    private List<TournamentBriefRepresentationDto> createTournamentBriefRepresentationDtoList(long playerId) {
        Date date = new Date(System.currentTimeMillis());
        List<Tour> upcomingTours = tourDao.findByDateGreaterThanEqual(date);
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
        List<Tour> upcomingTours = tourDao.findByDateGreaterThanEqual(date);
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

