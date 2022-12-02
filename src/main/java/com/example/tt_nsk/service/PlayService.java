package com.example.tt_nsk.service;

import com.example.tt_nsk.dao.PlayerDao;
import com.example.tt_nsk.entity.Player;
import com.example.tt_nsk.entity.Score;
import com.example.tt_nsk.entity.Scoring;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayService {
    private final Scoring scoring;
    private final Score score;
    private final PlayerDao playerDao;
    private final PlayerService playerService;

    public List<Double> getCurrentRatingAllPlayers() {
        List<Player> allActiveSortedByRating = playerService.findAllActiveSortedByRating();
        List<Double> ratingList = new ArrayList<>();
        for (Player player : allActiveSortedByRating) {
            ratingList.add(player.getRating().doubleValue());
        }
        return ratingList;
    }

    public int[] getNumberFromScore(String scoreString) {
        String[] split = scoreString.split("/[., /;:*-]/");
        int num1 = Integer.parseInt(split[0]);
        int num2 = Integer.parseInt(split[1]);
        int[] arrInt = {num1, num2};
        return arrInt;
    }

    public int[] getNumberFromScoreForArray(String[] split) {
        int num1 = Integer.parseInt(split[2]);
        int num2 = Integer.parseInt(split[3]);
        int[] arrInt = {num1, num2};
        return arrInt;
    }

    public int sumSet(int[] count) {
        return count[0] + count[1];
    }

    public boolean winnerPlayer1(int[] count) {
        return (count[0] > count[1]);
    }

    public double getCoefficientTour() {
        List<Double> currentRatingAllPlayers = getCurrentRatingAllPlayers();
        double sum = 0;
        for (Double b : currentRatingAllPlayers) {
            sum += Double.parseDouble(String.valueOf(b));
        }
        return Math.round(((sum / currentRatingAllPlayers.size()) / 2000) * 10.0) / 10.0;
    }

    public Map<String, Double[]> getMapResultTour(Score score) {
        Map<String, Double[]> countMap = new HashMap<>();
        countMap.put(score.getX1y2(), new Double[]{getCurrentRatingAllPlayers().get(0), getCurrentRatingAllPlayers().get(1)});
        countMap.put(score.getX1y3(), new Double[]{getCurrentRatingAllPlayers().get(0), getCurrentRatingAllPlayers().get(2)});
        countMap.put(score.getX1y4(), new Double[]{getCurrentRatingAllPlayers().get(0), getCurrentRatingAllPlayers().get(3)});
        countMap.put(score.getX1y5(), new Double[]{getCurrentRatingAllPlayers().get(0), getCurrentRatingAllPlayers().get(4)});

        return countMap;
    }

    public List<String> getListResultTour(Score score) {
        String scoreString = score.toString();
        List<String> listScore = new ArrayList<>(Arrays.asList(scoreString.split("', ")));
        return listScore;
    }

//    public static void main(String[] args) {
//        PlayService playService = new PlayService();
//        Score score = new Score();
//        score.setX1y2("2.3");
//        score.setX1y3("2.3");
//        score.setX1y4("6,2");
//        System.out.println(score);
//        System.out.println(playService.getListResultTour(score));
//        System.out.println(playService.getListResultTour(score).size());
//        System.out.println(playService.getListResultTour(score).get(1));
//    }

    public Map<String, Scoring> getResultTour(Score score, double coefficientTour) {
        List<String> listResultTour = getListResultTour(score);
        Map<String, Scoring> scoringMap = new HashMap<>();
        for (String str : listResultTour) {
            String[] split = str.split("/[xy'., /;:*-]/");
            if (split.length == 4) {
                if (scoringMap.containsKey(split[0])) {
                    Scoring scoringCurrent = scoringMap.get(split[0]);
                    Double deltaSet = scoringDeltaSet(split, coefficientTour);
                    scoringCurrent.setRating(scoringCurrent.getRating() - deltaSet);
                    scoringMap.put(split[0], scoringCurrent);
                }
                Scoring scoring = new Scoring();
                Double deltaSet = scoringDeltaSet(split, coefficientTour);
                scoring.setRating(getCurrentRatingAllPlayers().get(Integer.parseInt(split[0]) - 1) - deltaSet);
                scoring.setIndexPlayer(Integer.parseInt(split[0]) - 1);
                scoringMap.put(split[0], scoring);
            }
        }return scoringMap;
        }

//        public Scoring scoringTour (String[] split, Scoring scoring, double coefficientTour){
//            double ratingPlayerHighRating = getCurrentRatingAllPlayers().get(Integer.parseInt(split[0]) - 1);
//            double ratingPlayerLowRating = getCurrentRatingAllPlayers().get(Integer.parseInt(split[1]) - 1);
//            int[] numberFromScoreForArray = getNumberFromScoreForArray(split);
//            if (winnerPlayer1(numberFromScoreForArray)){
//                if ((ratingPlayerHighRating - ratingPlayerLowRating) > 100){
//                    scoring.setDelta(0);
//                }
//                scoring.setDelta((100 - ratingPlayerHighRating + ratingPlayerLowRating)/10 * coefficientTour);
//            }else scoring.setDelta(-(100 - ratingPlayerLowRating + ratingPlayerHighRating)/10 * coefficientTour);
//            scoring.setRating(ratingPlayerHighRating - scoring.getDelta());
//            scoring.setIndexPlayer(Integer.parseInt(split[0]) - 1);
//            return scoring;
//        }

    public Double scoringDeltaSet (String[] split, double coefficientTour){
        Double delta;
        double ratingPlayerHighRating = getCurrentRatingAllPlayers().get(Integer.parseInt(split[0]) - 1);
        double ratingPlayerLowRating = getCurrentRatingAllPlayers().get(Integer.parseInt(split[1]) - 1);
        int[] numberFromScoreForArray = getNumberFromScoreForArray(split);
        if (winnerPlayer1(numberFromScoreForArray)){
            if ((ratingPlayerHighRating - ratingPlayerLowRating) > 100){
            }
            delta = (100 - ratingPlayerHighRating + ratingPlayerLowRating)/10 * coefficientTour;
        }else delta = (-(100 - ratingPlayerLowRating + ratingPlayerHighRating)/10 * coefficientTour);
        return delta;
    }

}





//    let result11 = totalSet(getRating('player1'), getRating('player2'), f2, kt);
//
//
//
//    function totalSet(rating1, rating2, count, kt) {
//        let delta;
//        let r1 = Number(rating1);
//        let r2 = Number(rating2);
//        if (count == ""){
//            console.log(win(count))
//            delta = 0;
//        }else
//        if(win(count)){
//            if ((r1 - r2) > 100){
//                delta = 0;
//            }
//            delta = (100 - r1 + r2)/10 * kt;
//        }else delta = -(100 - r2 + r1)/10 * kt;
//        return delta;
//    }
//
//
//    public int sumSet(Player player, Score score1, Score score2, Score score3, Score score4){
//            int set;
//            return 0;
//        }
//
//    public int countSet() {
//        int score = scoring.score;
//        return scoring.score;
//    }
//
//    public Player saveRating(Player player) {
//        if (player.getId() != null) {
//            Optional<Player> playerFromDBOptional = playerDao.findById(player.getId());
//            if (playerFromDBOptional.isPresent()) {
//                Player playerFromDB = playerFromDBOptional.get();
//                playerFromDB.setRating(player.getRating());
//                return playerDao.save(playerFromDB);
//            }
//        }
//        return playerDao.save(player);
//    }
//
//    public double newRating;
//    }

//String[] parts = firstLine.split(" ");!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
