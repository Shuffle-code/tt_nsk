package com.example.tt_nsk.service;

import com.example.tt_nsk.dao.PlayerDao;
import com.example.tt_nsk.dao.PlayerTournamentRepo;
import com.example.tt_nsk.dto.PlayerBriefRepresentationDto;
import com.example.tt_nsk.entity.Player;
import com.example.tt_nsk.tournament.CurrentTournament;
import com.example.tt_nsk.tournament.TournamentData;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RefereeService {

    private final PlayerService playerService;
    private final PlayerTournamentRepo playerTournamentRepo;
    private final PlayerDao playerDao;
    private final ModelMapper modelMapper = new ModelMapper();

    public TournamentData startTournament(long tourId, int setsToWinGame) {
        List<PlayerBriefRepresentationDto> playerBriefRepresentationDtoListSortedByRatingDesc = getAllRegisteredPlayersOrderByRatingDesc(tourId);
        List<Pair<PlayerBriefRepresentationDto, PlayerBriefRepresentationDto>> playerPairs =
                playerService.dividePlayersIntoPairs(playerBriefRepresentationDtoListSortedByRatingDesc);
        List<List<String>> legUpTable = List.copyOf(compileLegUpTable(playerBriefRepresentationDtoListSortedByRatingDesc));
        List<TournamentData.Game> gameList = List.copyOf(playerPairs.stream().map(p -> new TournamentData.Game(p.getFirst(), p.getSecond())).collect(Collectors.toList()));

        TournamentData tournamentData = new TournamentData(tourId, legUpTable, gameList, true, setsToWinGame);
        CurrentTournament currentTournament = CurrentTournament.getInstance();
        currentTournament.startTour(tournamentData, setsToWinGame);
        return tournamentData;
    }

    public List<List<String>> compileLegUpTable(List<PlayerBriefRepresentationDto> playerBriefRepresentationDtoList) {
        if (playerBriefRepresentationDtoList.isEmpty()) {
            return Collections.emptyList();
        }
        Double highestRating = playerBriefRepresentationDtoList.get(0).getRating();
        List<List<String>> resultTable = new ArrayList<>();
        for (int y = 0; y < playerBriefRepresentationDtoList.size(); y++) {
            double mainPlayerRating = playerBriefRepresentationDtoList.get(y).getRating();
            List<String> row = new ArrayList<>();
            for (int x = 0; x < playerBriefRepresentationDtoList.size(); x++) {
                if (y == x) {
                    row.add("TT");
                } else /*if (highestRating > playerBriefRepresentationDtoList.get(j).getRating() && highestRating != 500)*/ {
                    double opponentPlayerRating = playerBriefRepresentationDtoList.get(x).getRating();
                    String str = calculateLegUp(mainPlayerRating, opponentPlayerRating);
                    row.add(str);
                }
            }
            resultTable.add(row);
        }
        return resultTable;
    }

    private String calculateLegUp(double mainPlayerRating, double opponentPlayerRating) {
        int legUp;
        double difference = Math.abs(mainPlayerRating - opponentPlayerRating);
        if (difference >= 0 && difference <= 25) {
            legUp = 0;
        } else if (difference > 25 && difference <= 50) {
            legUp = 1;
        } else if (difference > 50 && difference <= 75) {
            legUp = 2;
        } else if (difference > 75 && difference <= 100) {
            legUp = 3;
        } else if (difference > 100 && difference <= 125) {
            legUp = 4;
        } else if (difference > 125 && difference <= 150) {
            legUp = 5;
        } else if (difference > 150 && difference <= 175) {
            legUp = 6;
        } else {
            legUp = 7;
        }

        if (mainPlayerRating > opponentPlayerRating) {
            return "0/" + legUp;
        } else {
            return legUp + "/0";
        }
    }

    private List<PlayerBriefRepresentationDto> getAllRegisteredPlayersOrderByRatingDesc(Long tournamentId) {
        List<Long> playerIdList = playerTournamentRepo.findAllByTournamentId(tournamentId)
                .stream().map(pt -> pt.getPlayerId()).collect(Collectors.toList());
        List<Player> playerList = playerDao.findAllByIdsOrderByRatingDesc(playerIdList);
        return playerList.stream().map(player -> modelMapper.map(player, PlayerBriefRepresentationDto.class)).collect(Collectors.toList());
    }
}
