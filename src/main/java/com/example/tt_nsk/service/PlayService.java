package com.example.tt_nsk.service;

import com.example.tt_nsk.dao.TourDao;
import com.example.tt_nsk.dto.PlayerBriefRepresentationDto;
import com.example.tt_nsk.entity.LegUp;
import com.example.tt_nsk.entity.Player;
import com.example.tt_nsk.entity.Score;
import com.example.tt_nsk.entity.Scoring;
import com.example.tt_nsk.tournament.TournamentData;
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
//        List<Player> allActiveSortedByRating = getAllActiveSortedByRating();
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
//        System.out.println(getLegUpBeforeStartingTour(getCurrentRatingAllPlayers()));
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
            System.out.println("расчет по матчам");
        } else if (setWinSet.size() == setWin.size()) {
            place.sort(Comparator.comparing(Scoring::getSetWin).reversed());
            System.out.println("расчет по сетам");
        } else if (setWinSet.size() != setWin.size()) {
            place.sort(Comparator.comparing(Scoring::getDeltaWinLoss).reversed());
            System.out.println("расчет по разнице побед и поражений");
        } else {
            place.sort(Comparator.comparing(Scoring::getSetWin).reversed());
            System.out.println("расчет по разнице сетам в оставшихся случаях");
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
//        DecimalFormat df = new DecimalFormat("#,##");
//        System.out.println(Math.floor(delta * 100)/100);
//        BigDecimal bd = new BigDecimal(delta).setScale(2, RoundingMode.HALF_EVEN);
        return Math.floor(delta * 100) / 100;


//        return Math.floor(delta * 100)/100.0d;
//        return Double.valueOf(df.format(delta));
    }

    public String scoringLegUp(Double ratingPlayerHighRating, Double ratingPlayerLowRating) {
//        double ratingPlayerHighRating = getCurrentRatingAllPlayers().get(Integer.parseInt(split[1]) - 1);
//        double ratingPlayerLowRating = getCurrentRatingAllPlayers().get(Integer.parseInt(split[2])  - 1);
        double difference = ratingPlayerHighRating - ratingPlayerLowRating;
        if (difference >= 0 && difference <= 25) {
            return "0/0";
        } else if (difference > 25 && difference <= 50) {
            return "0/1";
        } else if (difference > 50 && difference <= 75) {
            return "0/2";
        } else if (difference > 75 && difference <= 100) {
            return "0/3";
        } else if (difference > 100 && difference <= 125) {
            return "0/4";
        } else if (difference > 125 && difference <= 150) {
            return "0/5";
        } else if (difference > 150 && difference <= 175) {
            return "0/6";
        }
        return "0/7";
    }

    public HashMap<String, String> getLegUpBeforeStartingTour(List<Double> currentRating) {
        HashMap<String, String> legUpStrArr = new HashMap<>();
        Double currentRatingElement = getAllActiveSortedByRating().get(0).getRating().doubleValue();
        for (int i = 0; i < currentRating.size() - 1; i++) {
            for (int j = 1; j < currentRating.size(); j++) {
                if (currentRatingElement > currentRating.get(j) && currentRatingElement != 500) {
                    legUpStrArr.put("fx" + (i + 1) + "y" + (j + 1), scoringLegUp(currentRatingElement, currentRating.get(j)));
                }
            }
            currentRatingElement = currentRating.get(i + 1);
        }
        return legUpStrArr;
    }

    public LegUp getLegUp(HashMap<String, String> mapLegUp) {
        LegUp legUp = new LegUp();
        if (mapLegUp.containsKey("fx1y2")) {
            legUp.setFx1y2(mapLegUp.get("fx1y2"));
            legUp.setFx2y1(mapLegUp.get("fx1y2").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx1y3")) {
            legUp.setFx1y3(mapLegUp.get("fx1y3"));
            legUp.setFx3y1(mapLegUp.get("fx1y3").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx1y4")) {
            legUp.setFx1y4(mapLegUp.get("fx1y4"));
            legUp.setFx4y1(mapLegUp.get("fx1y4").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx1y5")) {
            legUp.setFx1y5(mapLegUp.get("fx1y5"));
            legUp.setFx5y1(mapLegUp.get("fx1y5").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx1y6")) {
            legUp.setFx1y6(mapLegUp.get("fx1y6"));
            legUp.setFx6y1(mapLegUp.get("fx1y6").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx1y7")) {
            legUp.setFx1y7(mapLegUp.get("fx1y7"));
            legUp.setFx7y1(mapLegUp.get("fx1y7").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx1y8")) {
            legUp.setFx1y8(mapLegUp.get("fx1y8"));
            legUp.setFx8y1(mapLegUp.get("fx1y8").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx1y9")) {
            legUp.setFx1y9(mapLegUp.get("fx1y9"));
            legUp.setFx9y1(mapLegUp.get("fx1y9").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx1y10")) {
            legUp.setFx1y10(mapLegUp.get("fx1y10"));
            legUp.setFx10y1(mapLegUp.get("fx1y10").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx1y11")) {
            legUp.setFx1y11(mapLegUp.get("fx1y11"));
            legUp.setFx11y1(mapLegUp.get("fx1y11").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx1y12")) {
            legUp.setFx1y12(mapLegUp.get("fx1y12"));
            legUp.setFx12y1(mapLegUp.get("fx1y12").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx1y13")) {
            legUp.setFx1y13(mapLegUp.get("fx1y13"));
            legUp.setFx13y1(mapLegUp.get("fx1y13").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx2y3")) {
            legUp.setFx2y3(mapLegUp.get("fx2y3"));
            legUp.setFx3y2(mapLegUp.get("fx2y3").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx2y4")) {
            legUp.setFx2y4(mapLegUp.get("fx2y4"));
            legUp.setFx4y2(mapLegUp.get("fx2y4").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx2y5")) {
            legUp.setFx2y5(mapLegUp.get("fx2y5"));
            legUp.setFx5y2(mapLegUp.get("fx2y5").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx2y6")) {
            legUp.setFx2y6(mapLegUp.get("fx2y6"));
            legUp.setFx6y2(mapLegUp.get("fx2y6").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx2y7")) {
            legUp.setFx2y7(mapLegUp.get("fx2y7"));
            legUp.setFx7y2(mapLegUp.get("fx2y7").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx2y8")) {
            legUp.setFx2y8(mapLegUp.get("fx2y8"));
            legUp.setFx8y2(mapLegUp.get("fx2y8").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx2y9")) {
            legUp.setFx2y9(mapLegUp.get("fx2y9"));
            legUp.setFx9y2(mapLegUp.get("fx2y9").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx2y10")) {
            legUp.setFx2y10(mapLegUp.get("fx2y10"));
            legUp.setFx10y2(mapLegUp.get("fx2y10").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx2y11")) {
            legUp.setFx2y11(mapLegUp.get("fx2y11"));
            legUp.setFx11y2(mapLegUp.get("fx2y11").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx2y12")) {
            legUp.setFx2y12(mapLegUp.get("fx2y12"));
            legUp.setFx12y2(mapLegUp.get("fx2y12").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx2y13")) {
            legUp.setFx2y13(mapLegUp.get("fx2y13"));
            legUp.setFx13y2(mapLegUp.get("fx2y13").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx3y4")) {
            legUp.setFx3y4(mapLegUp.get("fx3y4"));
            legUp.setFx4y3(mapLegUp.get("fx3y4").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx3y5")) {
            legUp.setFx3y5(mapLegUp.get("fx3y5"));
            legUp.setFx5y3(mapLegUp.get("fx3y5").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx3y6")) {
            legUp.setFx3y6(mapLegUp.get("fx3y6"));
            legUp.setFx6y3(mapLegUp.get("fx3y6").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx3y7")) {
            legUp.setFx3y7(mapLegUp.get("fx3y7"));
            legUp.setFx7y3(mapLegUp.get("fx3y7").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx3y8")) {
            legUp.setFx3y8(mapLegUp.get("fx3y8"));
            legUp.setFx8y3(mapLegUp.get("fx3y8").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx3y9")) {
            legUp.setFx3y9(mapLegUp.get("fx3y9"));
            legUp.setFx9y3(mapLegUp.get("fx3y9").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx3y10")) {
            legUp.setFx3y10(mapLegUp.get("fx3y10"));
            legUp.setFx10y3(mapLegUp.get("fx3y10").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx3y11")) {
            legUp.setFx3y11(mapLegUp.get("fx3y11"));
            legUp.setFx11y3(mapLegUp.get("fx3y11").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx3y12")) {
            legUp.setFx3y12(mapLegUp.get("fx3y12"));
            legUp.setFx12y3(mapLegUp.get("fx3y12").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx3y13")) {
            legUp.setFx3y13(mapLegUp.get("fx3y13"));
            legUp.setFx13y3(mapLegUp.get("fx3y13").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx4y5")) {
            legUp.setFx4y5(mapLegUp.get("fx4y5"));
            legUp.setFx5y4(mapLegUp.get("fx4y5").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx4y6")) {
            legUp.setFx4y6(mapLegUp.get("fx4y6"));
            legUp.setFx6y4(mapLegUp.get("fx4y6").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx4y7")) {
            legUp.setFx4y7(mapLegUp.get("fx4y7"));
            legUp.setFx7y4(mapLegUp.get("fx4y7").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx4y8")) {
            legUp.setFx4y8(mapLegUp.get("fx4y8"));
            legUp.setFx8y4(mapLegUp.get("fx4y8").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx4y9")) {
            legUp.setFx4y9(mapLegUp.get("fx4y9"));
            legUp.setFx9y4(mapLegUp.get("fx4y9").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx4y10")) {
            legUp.setFx4y10(mapLegUp.get("fx4y10"));
            legUp.setFx10y4(mapLegUp.get("fx4y10").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx4y11")) {
            legUp.setFx4y11(mapLegUp.get("fx4y11"));
            legUp.setFx11y4(mapLegUp.get("fx4y11").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx4y12")) {
            legUp.setFx4y12(mapLegUp.get("fx4y12"));
            legUp.setFx12y4(mapLegUp.get("fx4y12").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx4y13")) {
            legUp.setFx4y13(mapLegUp.get("fx4y13"));
            legUp.setFx13y4(mapLegUp.get("fx4y13").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx5y6")) {
            legUp.setFx5y6(mapLegUp.get("fx5y6"));
            legUp.setFx6y5(mapLegUp.get("fx5y6").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx5y7")) {
            legUp.setFx5y7(mapLegUp.get("fx5y7"));
            legUp.setFx7y5(mapLegUp.get("fx5y7").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx5y8")) {
            legUp.setFx5y8(mapLegUp.get("fx5y8"));
            legUp.setFx8y5(mapLegUp.get("fx5y8").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx5y9")) {
            legUp.setFx5y9(mapLegUp.get("fx5y9"));
            legUp.setFx9y5(mapLegUp.get("fx5y9").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx5y10")) {
            legUp.setFx5y10(mapLegUp.get("fx5y10"));
            legUp.setFx10y5(mapLegUp.get("fx5y10").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx5y11")) {
            legUp.setFx5y11(mapLegUp.get("fx5y11"));
            legUp.setFx11y5(mapLegUp.get("fx5y11").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx5y12")) {
            legUp.setFx5y12(mapLegUp.get("fx5y12"));
            legUp.setFx12y5(mapLegUp.get("fx5y12").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx5y13")) {
            legUp.setFx5y13(mapLegUp.get("fx5y13"));
            legUp.setFx13y5(mapLegUp.get("fx5y13").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx6y7")) {
            legUp.setFx6y7(mapLegUp.get("fx6y7"));
            legUp.setFx7y6(mapLegUp.get("fx6y7").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx6y8")) {
            legUp.setFx6y8(mapLegUp.get("fx6y8"));
            legUp.setFx8y6(mapLegUp.get("fx6y8").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx6y9")) {
            legUp.setFx6y9(mapLegUp.get("fx6y9"));
            legUp.setFx9y6(mapLegUp.get("fx6y9").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx6y10")) {
            legUp.setFx6y10(mapLegUp.get("fx6y10"));
            legUp.setFx10y6(mapLegUp.get("fx6y10").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx6y11")) {
            legUp.setFx6y11(mapLegUp.get("fx6y11"));
            legUp.setFx11y6(mapLegUp.get("fx6y11").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx6y12")) {
            legUp.setFx6y12(mapLegUp.get("fx6y12"));
            legUp.setFx12y6(mapLegUp.get("fx6y12").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx6y13")) {
            legUp.setFx6y13(mapLegUp.get("fx6y13"));
            legUp.setFx13y6(mapLegUp.get("fx6y13").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx7y8")) {
            legUp.setFx7y8(mapLegUp.get("fx7y8"));
            legUp.setFx8y7(mapLegUp.get("fx7y8").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx7y9")) {
            legUp.setFx7y9(mapLegUp.get("fx7y9"));
            legUp.setFx9y7(mapLegUp.get("fx7y9").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx7y10")) {
            legUp.setFx7y10(mapLegUp.get("fx7y10"));
            legUp.setFx10y7(mapLegUp.get("fx7y10").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx7y11")) {
            legUp.setFx7y11(mapLegUp.get("fx7y11"));
            legUp.setFx11y7(mapLegUp.get("fx7y11").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx7y12")) {
            legUp.setFx7y12(mapLegUp.get("fx7y12"));
            legUp.setFx12y7(mapLegUp.get("fx7y12").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx7y13")) {
            legUp.setFx7y13(mapLegUp.get("fx7y13"));
            legUp.setFx13y7(mapLegUp.get("fx7y13").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx8y9")) {
            legUp.setFx8y9(mapLegUp.get("fx8y9"));
            legUp.setFx9y8(mapLegUp.get("fx8y9").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx8y10")) {
            legUp.setFx8y10(mapLegUp.get("fx8y10"));
            legUp.setFx10y8(mapLegUp.get("fx8y10").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx8y11")) {
            legUp.setFx8y11(mapLegUp.get("fx8y11"));
            legUp.setFx11y8(mapLegUp.get("fx8y11").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx8y12")) {
            legUp.setFx8y12(mapLegUp.get("fx8y12"));
            legUp.setFx12y8(mapLegUp.get("fx8y12").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx8y13")) {
            legUp.setFx8y13(mapLegUp.get("fx8y13"));
            legUp.setFx13y8(mapLegUp.get("fx8y13").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx9y10")) {
            legUp.setFx9y10(mapLegUp.get("fx9y10"));
            legUp.setFx10y9(mapLegUp.get("fx9y10").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx9y11")) {
            legUp.setFx9y11(mapLegUp.get("fx9y11"));
            legUp.setFx11y9(mapLegUp.get("fx9y11").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx9y12")) {
            legUp.setFx9y12(mapLegUp.get("fx9y12"));
            legUp.setFx12y9(mapLegUp.get("fx9y12").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx9y13")) {
            legUp.setFx9y13(mapLegUp.get("fx9y13"));
            legUp.setFx13y9(mapLegUp.get("fx9y13").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx10y11")) {
            legUp.setFx10y11(mapLegUp.get("fx10y11"));
            legUp.setFx11y10(mapLegUp.get("fx10y11").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx10y12")) {
            legUp.setFx10y12(mapLegUp.get("fx10y12"));
            legUp.setFx12y10(mapLegUp.get("fx10y12").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx10y13")) {
            legUp.setFx10y13(mapLegUp.get("fx10y13"));
            legUp.setFx13y10(mapLegUp.get("fx10y13").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx11y12")) {
            legUp.setFx11y12(mapLegUp.get("fx11y12"));
            legUp.setFx12y11(mapLegUp.get("fx11y12").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx11y13")) {
            legUp.setFx11y13(mapLegUp.get("fx11y13"));
            legUp.setFx13y11(mapLegUp.get("fx11y13").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        if (mapLegUp.containsKey("fx12y13")) {
            legUp.setFx12y13(mapLegUp.get("fx12y13"));
            legUp.setFx13y12(mapLegUp.get("fx12y13").replaceFirst("(.)(.*)(.)", "$3$2$1"));
        }
        return legUp;
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
                    String str = calculateLegUp(mainPlayerRating, opponentPlayerRating);
                    row.add(str);
                }

//                else {
//                    row.add("N/A");
//                }
            }
            resultTable.add(row);
        }
        return resultTable;
    }

//    public Map<String, Scoring> getResultTour(List<String> listResultTour) {
//        double coefficientTour = getCoefficientTour();
//        Map<String, Scoring> scoringMap = writeMapWithNullScore();
//        for (int i = 0; i < listResultTour.size(); i++) {
//            String[] split = listResultTour.get(i).split("[xy='.,/ ;:*-]+");
//            switch (split[1]){
//                case ("1"):
//                    writeScoreInMap(split, scoringMap, coefficientTour);
//                    break;
//                case ("2"):
//                    writeScoreInMap(split, scoringMap, coefficientTour);
//                    break;
//                case ("3"):
//                    writeScoreInMap(split, scoringMap, coefficientTour);
//                    break;
//                case ("4"):
//                    writeScoreInMap(split, scoringMap, coefficientTour);
//                    break;
//                case ("5"):
//                    writeScoreInMap(split, scoringMap, coefficientTour);
//                    break;
//                case ("6"):
//                    writeScoreInMap(split, scoringMap, coefficientTour);
//                    break;
//                case ("7"):
//                    writeScoreInMap(split, scoringMap, coefficientTour);
//                    break;
//                case ("8"):
//                    writeScoreInMap(split, scoringMap, coefficientTour);
//                    break;
//                case ("9"):
//                    writeScoreInMap(split, scoringMap, coefficientTour);
//                    break;
//                case ("10"):
//                    writeScoreInMap(split, scoringMap, coefficientTour);
//                    break;
//                case ("11"):
//                    writeScoreInMap(split, scoringMap, coefficientTour);
//                    break;
//                case ("12"):
//                    writeScoreInMap(split, scoringMap, coefficientTour);
//                    break;
//                case ("13"):
//                    writeScoreInMap(split, scoringMap, coefficientTour);
//                    break;
//                default:writeMapWithNullScore();
//                    break;
//            }
//        }return scoringMap;
//    }

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
//        DecimalFormat df = new DecimalFormat("#,##");
//        System.out.println(Math.floor(delta * 100)/100);
//        BigDecimal bd = new BigDecimal(delta).setScale(2, RoundingMode.HALF_EVEN);
        return Math.floor(delta * 100)/100;
    }

    public Map<String, Scoring> writeMapWithNullScore (List<Player> allActiveSortedByRating){
        Map<String, Scoring> scoringMap = new HashMap<>();
//        List<Player> allActiveSortedByRating = getAllActiveSortedByRating();
        for (int i = 0; i < getCurrentRatingAllPlayersForSelectTour(allActiveSortedByRating).size(); i++) {
            Scoring scoring = new Scoring();
            scoring.setRating(getCurrentRatingAllPlayersForSelectTour(allActiveSortedByRating).get((i)));
            scoring.setPlacePlayer(i + 1);
            scoring.setIdPlayer(allActiveSortedByRating.get(i).getId());
            scoringMap.put(String.valueOf(i), scoring);
        }
        return scoringMap;
    }

    public HashMap<String, String> getLegUpBeforeStartingTour(List<Double> currentRating, List<Player> playerList){
        HashMap<String, String> legUpStrArr = new HashMap<>();
        Double currentRatingElement = playerList.get(0).getRating().doubleValue();
        for (int i = 0; i < currentRating.size() - 1 ; i++) {
            for (int j = 1; j < currentRating.size(); j++) {
                if (currentRatingElement > currentRating.get(j) && currentRatingElement != 500) {
                    legUpStrArr.put("fx" + (i + 1) + "y" + (j + 1), scoringLegUp(currentRatingElement, currentRating.get(j)));
                }
            }
            currentRatingElement = currentRating.get(i + 1);
        }
        return legUpStrArr;
    }

    private String calculateLegUp(double mainPlayerRating, double opponentPlayerRating) {
        int legUp;
        double difference = Math.abs(mainPlayerRating - opponentPlayerRating);
        if (difference >= 0 && difference <= 25){
            legUp =  0;
        } else if (difference > 25 && difference <= 50){
            legUp =  1;
        } else if (difference > 50 && difference <= 75){
            legUp =  2;
        } else if (difference > 75 && difference <= 100){
            legUp =  3;
        } else if (difference > 100 && difference <= 125){
            legUp =  4;
        } else if (difference > 125 && difference  <= 150){
            legUp =  5;
        } else if (difference > 150 && difference <= 175) {
            legUp =  6;
        } else {
            legUp = 7;
        }

        if (mainPlayerRating > opponentPlayerRating) {
            return "0/" + legUp;
        } else {
            return legUp +"/0";
        }
    }


}