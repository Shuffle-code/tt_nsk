package site.tt_nsk.service;

import site.tt_nsk.dao.TourDao;
import site.tt_nsk.dto.PlayerBriefRepresentationDto;
import site.tt_nsk.entity.LegUp;
import site.tt_nsk.entity.Player;
import site.tt_nsk.entity.Score;
import site.tt_nsk.entity.Scoring;
import site.tt_nsk.tournament.TournamentData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayService {
    private final TourDao tourDao;
    private final TourService tourService;
    private final PlayerService playerService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Base64.Encoder encoder = Base64.getEncoder();
    private final Base64.Decoder decoder = Base64.getDecoder();
    private final LegUpService legUpService;
    public List<Double> getCurrentRatingAllPlayersForSelectTour(List<Player> allActiveSortedByRating) {
//        List<Player> allActiveSortedByRating = getAllActiveSortedByRating();
        List<Double> ratingList = new ArrayList<>();
        for (Player player : allActiveSortedByRating) {
            ratingList.add(player.getRating().doubleValue());
        }
        return ratingList;
    }

    public List<Double> getCurrentRatingAllPlayersForSelectTour(Long id) {
        List<Player> allActiveSortedByRating = tourService.getListPlayersForFutureTour(tourService.findAllByTourId(id));
        List<Double> ratingList = new ArrayList<>();
        for (Player player : allActiveSortedByRating) {
            ratingList.add(player.getRating().doubleValue());
        }
        return ratingList;
    }

    public List<Double> getCurrentRatingAllPlayers() {
        List<Player> allActiveSortedByRating = getAllActiveSortedByRating();
        List<Double> ratingList = new ArrayList<>();
        for (Player player : allActiveSortedByRating) {
            ratingList.add(player.getRating().doubleValue());
        }
        return ratingList;
    }

    public List<Double> getCurrentRatingAllPlayers(List<Player> allActiveSortedByRating) {
        List<Double> ratingList = new ArrayList<>();
        for (Player player : allActiveSortedByRating) {
            ratingList.add(player.getRating().doubleValue());
        }
        return ratingList;
    }

    public List<String> arrayWithoutNull(ArrayList<String> arrayResult) {
        List<String> withoutNull = new ArrayList();
        for (String cellScore : arrayResult) {
            String[] split = cellScore.split("=");
            if ((split.length == 1) || split[1].equals("'null'") || split[1].equals("''")) {
            } else withoutNull.add(cellScore);
        }
        return withoutNull;
    }

    public int getSizeArrayList(List<String> list) {
        return list.size();
    }

    public int[] getNumbersFromScoreForArray(String[] split) {
        int num1 = Integer.parseInt(split[3]);
        int num2 = Integer.parseInt(split[4]);
        int[] arrInt = {num1, num2};
        return arrInt;
    }

    public int sumSet(int[] count) {
        return count[0] + count[1];
    }

    public boolean winnerPlayer1(int[] count) {
        return (count[0] > count[1]);
    }

    public int sumWinSet(int[] count) {
        return count[0];
    }

    public int sumWin(int[] count) {
        int a = count[0];
        int b = count[1];
        if (a > b) return 1;
        else return 0;
    }

    public double getCoefficientTour() {
        List<Double> currentRatingAllPlayers = getCurrentRatingAllPlayers();
        double sum = 0;
        for (Double b : currentRatingAllPlayers) {
            sum += Double.parseDouble(String.valueOf(b));
        }
        return Math.round(((sum / currentRatingAllPlayers.size()) / 4000) * 10.0) / 10.0;
    }

    public ArrayList<String> getListResultTour(Score score) {
        String scoreString = score.toString();
        ArrayList<String> listScore = new ArrayList<>(Arrays.asList(scoreString.split(", ")));
        return listScore;
    }

    public void placePlayer(Map<String, Scoring> scoringMap) {
        List<Scoring> place = getListFromMap(scoringMap);
        List<Integer> setWin = new ArrayList<>();
        List<Integer> win = new ArrayList<>();
        List<Integer> setLoss = new ArrayList<>();
        Set<Scoring> set = new TreeSet<>(new WinComparator());
        List<Scoring> duplicates = new ArrayList<>();
        for (Scoring sc : place) {
            setWin.add(sc.getSetWin());
            win.add(sc.getCountWin());
            setLoss.add(sc.getSet() - sc.getSetWin());
            if (!set.add(sc)) {
                duplicates.add(sc);
            }
            sc.setDeltaWinLoss(sc.getSetWin() - (sc.getSet() - sc.getSetWin()));
        }
        Set<Integer> setWinSet = new HashSet<>(setWin);
        if (duplicates.size() == 0) {
            place.sort(Comparator.comparing(Scoring::getCountWin).reversed());
            log.info("расчет по матчам");
        } else if (setWinSet.size() == setWin.size()) {
            place.sort(Comparator.comparing(Scoring::getSetWin).reversed());
            log.info("расчет по сетам");
        } else if (setWinSet.size() != setWin.size()) {
            place.sort(Comparator.comparing(Scoring::getDeltaWinLoss).reversed());
            log.info("расчет по разнице побед и поражений");
        } else {
            place.sort(Comparator.comparing(Scoring::getSetWin).reversed());
            log.info("расчет по разнице сетам в оставшихся случаях");
        }
//        добавил в Map значение занятых мест
        List<Integer> arrayPlace = new ArrayList<>();
        for (Scoring sc : place) {
            arrayPlace.add(sc.getIndexPlayer());
        }
        scoringMap.forEach((k, v) -> v.setPlacePlayer(arrayPlace.indexOf(v.getIndexPlayer()) + 1));
    }

    public Long getIdFirstPlace(Map<String, Scoring> scoringMap) {
        List<Scoring> listFromMap = getListFromMap(scoringMap);
        for (Scoring sc : listFromMap) {
            if (sc.getPlacePlayer() == 1) {
                return sc.getIdPlayer();
            }
        }
        return listFromMap.get(1).getIdPlayer();
    }

    public List<Scoring> getListFromMap(Map<String, Scoring> scoringMap) {
        Collection<Scoring> values = scoringMap.values();
        List<Scoring> place = new ArrayList<>();
        place.addAll(values);
        return place;
    }

    public String createCurrentTournamentState(TournamentData tournamentData) {
        String tournamentDataString;
        try {
            tournamentDataString = objectMapper.writeValueAsString(tournamentData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
        String encodedTournament = encoder.encodeToString(tournamentDataString.getBytes());
        return encodedTournament;
    }

    public Optional<TournamentData> restoreTournament(long tourId) {
        Optional<String> currentTournamentStringOptional = tourDao.findCurrentTournamentById(tourId);
        if (currentTournamentStringOptional.isPresent()) {
            byte[] byteArray = decoder.decode(currentTournamentStringOptional.get());
            TournamentData tournamentData;
            try {
                tournamentData = objectMapper.readValue(byteArray, TournamentData.class);
                return Optional.of(tournamentData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }


    public class WinComparator implements Comparator<Scoring> {
        @Override
        public int compare(Scoring win1, Scoring win2) {
            return win1.getCountWin() - win2.getCountWin();
        }

    }

    public void writeScoreInMap(String[] split, Map<String, Scoring> scoringMap, double coefficientTour) {
        int i = Integer.parseInt(split[1]);
        Scoring scoringCurrent = scoringMap.get(String.valueOf(i - 1));
        Double deltaSet = scoringDeltaSet(split, coefficientTour);
        scoringCurrent.setRating(scoringCurrent.getRating() + deltaSet);
        scoringCurrent.setDelta(scoringCurrent.getDelta() + deltaSet);
        scoringCurrent.setSet(scoringCurrent.getSet() + sumSet(getNumbersFromScoreForArray(split)));
        scoringCurrent.setSetWin(scoringCurrent.getSetWin() + sumWinSet(getNumbersFromScoreForArray(split)));
        scoringCurrent.setCountWin(scoringCurrent.getCountWin() + sumWin(getNumbersFromScoreForArray(split)));
        scoringCurrent.setIndexPlayer(Integer.parseInt(split[1]));
        scoringMap.put(String.valueOf(i - 1), scoringCurrent);
    }

    public List<Player> getAllActiveSortedByRating() {
        return playerService.findAllActiveSortedByRating();
    }

    public Map<String, Scoring> writeMapWithNullScore() {
        Map<String, Scoring> scoringMap = new HashMap<>();
        List<Player> allActiveSortedByRating = getAllActiveSortedByRating();
        for (int i = 0; i < getCurrentRatingAllPlayers().size(); i++) {
            Scoring scoring = new Scoring();
            scoring.setRating(getCurrentRatingAllPlayers().get((i)));
            scoring.setPlacePlayer(i + 1);
            scoring.setIdPlayer(allActiveSortedByRating.get(i).getId());
            scoringMap.put(String.valueOf(i), scoring);
        }
        return scoringMap;
    }

    public Map<String, Scoring> getResultTour(List<String> listResultTour) {
        double coefficientTour = getCoefficientTour();
        Map<String, Scoring> scoringMap = writeMapWithNullScore();
        for (int i = 0; i < listResultTour.size(); i++) {
            String[] split = listResultTour.get(i).split("[xy='.,/ ;:*-]+");
            switch (split[1]) {
                case ("1"):
                    writeScoreInMap(split, scoringMap, coefficientTour);
                    break;
                case ("2"):
                    writeScoreInMap(split, scoringMap, coefficientTour);
                    break;
                case ("3"):
                    writeScoreInMap(split, scoringMap, coefficientTour);
                    break;
                case ("4"):
                    writeScoreInMap(split, scoringMap, coefficientTour);
                    break;
                case ("5"):
                    writeScoreInMap(split, scoringMap, coefficientTour);
                    break;
                case ("6"):
                    writeScoreInMap(split, scoringMap, coefficientTour);
                    break;
                case ("7"):
                    writeScoreInMap(split, scoringMap, coefficientTour);
                    break;
                case ("8"):
                    writeScoreInMap(split, scoringMap, coefficientTour);
                    break;
                case ("9"):
                    writeScoreInMap(split, scoringMap, coefficientTour);
                    break;
                case ("10"):
                    writeScoreInMap(split, scoringMap, coefficientTour);
                    break;
                case ("11"):
                    writeScoreInMap(split, scoringMap, coefficientTour);
                    break;
                case ("12"):
                    writeScoreInMap(split, scoringMap, coefficientTour);
                    break;
                case ("13"):
                    writeScoreInMap(split, scoringMap, coefficientTour);
                    break;
                default:
                    writeMapWithNullScore();
                    break;
            }
        }
        return scoringMap;
    }

    public Double scoringDeltaSet(String[] split, double coefficientTour) {
        Double delta;
        double ratingPlayerHighRating = getCurrentRatingAllPlayers().get(Integer.parseInt(split[1]) - 1);
        double ratingPlayerLowRating = getCurrentRatingAllPlayers().get(Integer.parseInt(split[2]) - 1);
        int[] numberFromScoreForArray = getNumbersFromScoreForArray(split);
        if (winnerPlayer1(numberFromScoreForArray)) {
            if ((ratingPlayerHighRating - ratingPlayerLowRating) > 200) {
                delta = 0.0;
            } else delta = (200 - ratingPlayerHighRating + ratingPlayerLowRating) / 10 * coefficientTour;
        } else if ((ratingPlayerLowRating - ratingPlayerHighRating) > 200) {
            delta = 0.0;
        } else delta = (-(200 - ratingPlayerLowRating + ratingPlayerHighRating) / 10 * coefficientTour);
        return Math.floor(delta * 100) / 100;
    }

    public List<List<String>> compileResultTable(List<PlayerBriefRepresentationDto> playerBriefRepresentationDtoList) {
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
                    String str = legUpService.calculateLegUp(mainPlayerRating, opponentPlayerRating);
                    row.add(str);
                }
            }
            resultTable.add(row);
        }
        return resultTable;
    }

    public Map<String, Scoring> getResultTour(List<String> listResultTour, List<Player> allActiveSortedByRating ) {
        double coefficientTour = getCoefficientTour();
        Map<String, Scoring> scoringMap = writeMapWithNullScore(allActiveSortedByRating);
        for (int i = 0; i < listResultTour.size(); i++) {
            String[] split = listResultTour.get(i).split("[xy='.,/ ;:*-]+");
            switch (split[1]){
                case ("1"):
                    writeScoreInMap(split, scoringMap, coefficientTour, allActiveSortedByRating);
                    break;
                case ("2"):
                    writeScoreInMap(split, scoringMap, coefficientTour, allActiveSortedByRating);
                    break;
                case ("3"):
                    writeScoreInMap(split, scoringMap, coefficientTour, allActiveSortedByRating);
                    break;
                case ("4"):
                    writeScoreInMap(split, scoringMap, coefficientTour, allActiveSortedByRating);
                    break;
                case ("5"):
                    writeScoreInMap(split, scoringMap, coefficientTour, allActiveSortedByRating);
                    break;
                case ("6"):
                    writeScoreInMap(split, scoringMap, coefficientTour,allActiveSortedByRating);
                    break;
                case ("7"):
                    writeScoreInMap(split, scoringMap, coefficientTour, allActiveSortedByRating);
                    break;
                case ("8"):
                    writeScoreInMap(split, scoringMap, coefficientTour, allActiveSortedByRating);
                    break;
                case ("9"):
                    writeScoreInMap(split, scoringMap, coefficientTour, allActiveSortedByRating);
                    break;
                case ("10"):
                    writeScoreInMap(split, scoringMap, coefficientTour, allActiveSortedByRating);
                    break;
                case ("11"):
                    writeScoreInMap(split, scoringMap, coefficientTour, allActiveSortedByRating);
                    break;
                case ("12"):
                    writeScoreInMap(split, scoringMap, coefficientTour, allActiveSortedByRating);
                    break;
                case ("13"):
                    writeScoreInMap(split, scoringMap, coefficientTour, allActiveSortedByRating);
                    break;
                default:writeMapWithNullScore(allActiveSortedByRating);
                    break;
            }
        }return scoringMap;
    }

    public void writeScoreInMap(String[] split, Map<String, Scoring> scoringMap, double coefficientTour, List<Player> allActiveSortedByRating){
        int i = Integer.parseInt(split[1]);
        Scoring scoringCurrent = scoringMap.get(String.valueOf(i - 1));
        Double deltaSet = scoringDeltaSet(split, coefficientTour, allActiveSortedByRating);
        scoringCurrent.setRating(scoringCurrent.getRating() + deltaSet);
        scoringCurrent.setDelta(scoringCurrent.getDelta() + deltaSet);
        scoringCurrent.setSet(scoringCurrent.getSet() + sumSet(getNumbersFromScoreForArray(split)));
        scoringCurrent.setSetWin(scoringCurrent.getSetWin() + sumWinSet(getNumbersFromScoreForArray(split)));
        scoringCurrent.setCountWin(scoringCurrent.getCountWin() + sumWin(getNumbersFromScoreForArray(split)));
        scoringCurrent.setIndexPlayer(Integer.parseInt(split[1]));
        scoringMap.put(String.valueOf(i - 1), scoringCurrent);
    }

    public Double scoringDeltaSet (String[] split, double coefficientTour, List<Player> allActiveSortedByRating){
        Double delta;
        double ratingPlayerHighRating = getCurrentRatingAllPlayers(allActiveSortedByRating).get(Integer.parseInt(split[1]) - 1);
        double ratingPlayerLowRating = getCurrentRatingAllPlayers(allActiveSortedByRating).get(Integer.parseInt(split[2])  - 1);
        int[] numberFromScoreForArray = getNumbersFromScoreForArray(split);
        if (winnerPlayer1(numberFromScoreForArray)){
            if ((ratingPlayerHighRating - ratingPlayerLowRating ) > 200){
                delta = 0.0;
            } else delta = (200 - ratingPlayerHighRating + ratingPlayerLowRating)/10 * coefficientTour;
        }else if ((ratingPlayerLowRating - ratingPlayerHighRating ) > 200) {
            delta = 0.0;
        }else delta = (-(200 - ratingPlayerLowRating + ratingPlayerHighRating)/10 * coefficientTour);
        return Math.floor(delta * 100)/100;
    }

    public Map<String, Scoring> writeMapWithNullScore (List<Player> allActiveSortedByRating){
        Map<String, Scoring> scoringMap = new HashMap<>();
        for (int i = 0; i < getCurrentRatingAllPlayersForSelectTour(allActiveSortedByRating).size(); i++) {
            Scoring scoring = new Scoring();
            scoring.setRating(getCurrentRatingAllPlayersForSelectTour(allActiveSortedByRating).get((i)));
            scoring.setPlacePlayer(i + 1);
            scoring.setIdPlayer(allActiveSortedByRating.get(i).getId());
            scoringMap.put(String.valueOf(i), scoring);
        }
        return scoringMap;
    }
}