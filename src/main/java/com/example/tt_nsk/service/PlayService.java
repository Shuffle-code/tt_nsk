package com.example.tt_nsk.service;

import com.example.tt_nsk.entity.Player;
import com.example.tt_nsk.entity.Score;
import com.example.tt_nsk.entity.Scoring;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.internal.Collections;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayService {
    private final PlayerService playerService;

    public List<Double> getCurrentRatingAllPlayers() {
        List<Player> allActiveSortedByRating = playerService.findAllActiveSortedByRating();
        List<Double> ratingList = new ArrayList<>();
        for (Player player : allActiveSortedByRating) {
            ratingList.add(player.getRating().doubleValue());
        }
        return ratingList;
    }
//
//    public int[] getNumberFromScore(String scoreString) {
//        String[] split = scoreString.split("/[., /;:*-]/");
//        int num1 = Integer.parseInt(split[0]);
//        int num2 = Integer.parseInt(split[1]);
//        int[] arrInt = {num1, num2};
//        return arrInt;
//    }

    public List<String> arrayWithoutNull(ArrayList<String> arrayResult){
        List<String> withoutNull = new ArrayList();
        for (String cellScore : arrayResult) {
            String[] split = cellScore.split("=");
            if ((split.length == 1) || split[1].equals("'null'") || split[1].equals("''")){

            }else withoutNull.add(cellScore);

        }
        return withoutNull;
    }


    public int[] getNumberFromScoreForArray(String[] split) {
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

    public int sumWinSet (int[] count){
      return count[0];
    }

    public int sumWin (int[] count){
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
        return Math.round(((sum / currentRatingAllPlayers.size()) / 2000) * 10.0) / 10.0;
    }

    public ArrayList<String> getListResultTour(Score score) {
        String scoreString = score.toString();
        ArrayList<String> listScore = new ArrayList<>(Arrays.asList(scoreString.split(", ")));
        return listScore;
    }

    public void placePlayer(Map<String, Scoring> scoringMap){
        Collection<Scoring> values = scoringMap.values();
        List<Scoring> place = new ArrayList<>();
        place.addAll(values);
        List<Integer> setWin = new ArrayList<>();
        List<Integer> win = new ArrayList<>();
        List<Integer> setLoss = new ArrayList<>();
        Set<Scoring> set = new TreeSet<>(new WinComparator());
        List<Scoring> duplicates = new ArrayList<>();
        for (Scoring sc : place) {
            setWin.add(sc.getSetWin());
            win.add(sc.getCountWin());
            setLoss.add(sc.getSet() - sc.getSetWin());
            if (!set.add(sc)){
                duplicates.add(sc);
            }
            sc.setDeltaWinLoss(sc.getSetWin() - (sc.getSet() - sc.getSetWin()));
        }
        Set<Integer> setWinSet = new HashSet<>(setWin);
        if (duplicates.size() == 0){
            place.sort(Comparator.comparing(Scoring :: getCountWin).reversed());
            System.out.println("расчет по матчам");
        } else if (setWinSet.size() == setWin.size()){
            place.sort(Comparator.comparing(Scoring :: getSetWin).reversed());
            System.out.println("расчет по сетам");
        } else if (setWinSet.size() != setWin.size()){
            place.sort(Comparator.comparing(Scoring :: getDeltaWinLoss).reversed());
            System.out.println("расчет по разнице побед и поражений");
        } else {
            place.sort(Comparator.comparing(Scoring :: getSetWin).reversed());
            System.out.println("расчет по разнице сетам в оставшихся случаях");
        }
//        добавил в Мапу значение занятых мест
        List<Integer> arrayPlace = new ArrayList<>();
        for (Scoring sc : place) {
            arrayPlace.add(sc.getIndexPlayer());
        }
        scoringMap.forEach((k, v) -> v.setPlacePlayer(arrayPlace.indexOf(v.getIndexPlayer()) + 1));
    }

    public class WinComparator implements Comparator<Scoring>
    {
        @Override
        public int compare(Scoring win1, Scoring win2)
        {
            return win1.getCountWin() - win2.getCountWin();
        }

    }
    public void writeScoreInMap(String[] split, Map<String, Scoring> scoringMap, double coefficientTour){
        int i = Integer.parseInt(split[1]);
        Scoring scoringCurrent = scoringMap.get(String.valueOf(i - 1));
        Double deltaSet = scoringDeltaSet(split, coefficientTour);
        scoringCurrent.setRating(scoringCurrent.getRating() + deltaSet);
        scoringCurrent.setDelta(scoringCurrent.getDelta() + deltaSet);
        scoringCurrent.setSet(scoringCurrent.getSet() + sumSet(getNumberFromScoreForArray(split)));
        scoringCurrent.setSetWin(scoringCurrent.getSetWin() + sumWinSet(getNumberFromScoreForArray(split)));
        scoringCurrent.setCountWin(scoringCurrent.getCountWin() + sumWin(getNumberFromScoreForArray(split)));
        scoringCurrent.setIndexPlayer(Integer.parseInt(split[1]));
        scoringMap.put(String.valueOf(i - 1), scoringCurrent);
    }

    public Map<String, Scoring> writeMapWithNullScore (){
        Map<String, Scoring> scoringMap = new HashMap<>();
        for (int i = 0; i < getCurrentRatingAllPlayers().size(); i++) {
            Scoring scoring = new Scoring();
            scoring.setRating(getCurrentRatingAllPlayers().get((i)));
            scoring.setPlacePlayer(i + 1);
            scoringMap.put(String.valueOf(i), scoring);
        }
        return scoringMap;
    }

    public Map<String, Scoring> getResultTour(List<String> listResultTour) {
        double coefficientTour = getCoefficientTour();
        Map<String, Scoring> scoringMap = writeMapWithNullScore();
        for (int i = 0; i < listResultTour.size(); i++) {
            String[] split = listResultTour.get(i).split("[xy='.,/ ;:*-]+");
            switch (split[1]){
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
                default:writeMapWithNullScore();
                    break;
                }
            }return scoringMap;
        }

    public Double scoringDeltaSet (String[] split, double coefficientTour){
        Double delta;
        double ratingPlayerHighRating = getCurrentRatingAllPlayers().get(Integer.parseInt(split[1]) - 1);
        double ratingPlayerLowRating = getCurrentRatingAllPlayers().get(Integer.parseInt(split[2])  - 1);
        int[] numberFromScoreForArray = getNumberFromScoreForArray(split);
        if (winnerPlayer1(numberFromScoreForArray)){
            if ((ratingPlayerHighRating - ratingPlayerLowRating ) > 100){
                delta = 0.0;
            } else delta = (100 - ratingPlayerHighRating + ratingPlayerLowRating)/10 * coefficientTour;
        }else if ((ratingPlayerLowRating - ratingPlayerHighRating ) > 100) {
            delta = 0.0;
        }else delta = (-(100 - ratingPlayerLowRating + ratingPlayerHighRating)/10 * coefficientTour);
        return delta;
    }
}
