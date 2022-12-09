package com.example.tt_nsk;

import com.example.tt_nsk.entity.Score;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class testStarting {
    public static void main(String[] args) {


//        PlayService playService = new PlayService();
        Score score = new Score();
        score.setX1y2("2.3");
        score.setX1y3("2.3");
        score.setX1y4("6,2");
        score.setX2y3("3-2");
        System.out.println(score);
        System.out.println(getListResultTour(score));
        System.out.println(getListResultTour(score).size());
        System.out.println(getListResultTour(score).get(1));
    }
   @Test
    public static List<String> getListResultTour(Score score) {
        String scoreString = score.toString();
        List<String> listScore = new ArrayList<>(Arrays.asList(scoreString.split("', ")));
        return listScore;
    }
}
