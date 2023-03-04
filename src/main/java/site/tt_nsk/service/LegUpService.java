package site.tt_nsk.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.tt_nsk.entity.LegUp;
import site.tt_nsk.entity.Player;

import java.util.HashMap;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class LegUpService {

    private final PlayerService playerService;

    public HashMap<String, String> getLegUpBeforeStartingTour(List<Double> currentRating) {
        HashMap<String, String> legUpStrArr = new HashMap<>();
        Double currentRatingElement = playerService.findAllActiveSortedByRating().get(0).getRating().doubleValue();
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


    public HashMap<String, String> getLegUpBeforeStartingTour(List<Double> currentRating, List<Player> playerList){
        HashMap<String, String> legUpStrArr = new HashMap<>();
        if (playerList.size() == 0){
            return null;
        }else {
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
    }

    public String scoringLegUp(Double ratingPlayerHighRating, Double ratingPlayerLowRating) {
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

    String calculateLegUp(double mainPlayerRating, double opponentPlayerRating) {
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
    public LegUp getLegUp(HashMap<String, String> mapLegUp) {
        if (mapLegUp == null){
            return null;
        }else {
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
    }


}
