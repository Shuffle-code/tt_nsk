package com.example.tt_nsk.controller;
import com.example.tt_nsk.entity.Player;
import com.example.tt_nsk.entity.Score;
import com.example.tt_nsk.entity.Scoring;
import com.example.tt_nsk.service.PlayService;
import com.example.tt_nsk.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tour")
public class PlayController {
    private final PlayService playService;
    private final TourController tourController;
    private final PlayerService playerService;

    @PostMapping("/count")
    public String scoringTour(Score score, Model model, HttpSession httpSession) {
        List<Player> allActiveSortedByRating = playerService.findAllActiveSortedByRating();
        switch (allActiveSortedByRating.size()) {
            case 3:
                tourController.createListPlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 4:
                tourController.createListFor4PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 5:
                tourController.createListFor5PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 6:
                tourController.createListFor6PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 7:
                tourController.createListFor7PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 8:
                tourController.createListFor8PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 9:
                tourController.createListFor9PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 10:
                tourController.createListFor10PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 11:
                tourController.createListFor11PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 12:
                tourController.createListFor12PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
            case 13:
                tourController.createListFor13PlayersTour(model, httpSession, allActiveSortedByRating);
                break;
        }
//        tourController.createListPlayersTour(model, httpSession, allActiveSortedByRating);
        List<String> list = playService.arrayWithoutNull(playService.getListResultTour(score));
        System.out.println(list);
        Map<String, Scoring> resultTour = playService.getResultTour(list);
        playService.placePlayer(resultTour);
        return returnPageScoring(allActiveSortedByRating, model, resultTour);
//        tourController.addAttributeFor3Model(resultTour, model);
//        model.addAttribute("result1", resultTour.get("0"));
//        model.addAttribute("result2", resultTour.get("1"));
//        model.addAttribute("result3", resultTour.get("2"));
//        System.out.println("++" + playService.placePlayer(resultTour));
//        return "tour/tour-form-server-for3players";
    }

    public String returnPageScoring(List<Player> allActiveSortedByRating, Model model, Map<String, Scoring> resultTour) {
        switch (allActiveSortedByRating.size()) {
            case 3:
                tourController.addAttributeFor3Model(resultTour, model);
                return "tour/tour-form-server-for3players";
            case 4:
                tourController.addAttributeFor4Model(resultTour, model);
                return "tour/tour-form-server-for4players";
            case 5:
                tourController.addAttributeFor5Model(resultTour, model);
                return "tour/tour-form-server-for5players";
            case 6:
                tourController.addAttributeFor6Model(resultTour, model);
                return "tour/tour-form-server-for6players";
            case 7:
                tourController.addAttributeFor7Model(resultTour, model);
                return "tour/tour-form-server-for7players";
            case 8:
                tourController.addAttributeFor8Model(resultTour, model);
                return "tour/tour-form-server-for8players";
            case 9:
                tourController.addAttributeFor9Model(resultTour, model);
                return "tour/tour-form-server-for9players";
            case 10:
                tourController.addAttributeFor10Model(resultTour, model);
                return "tour/tour-form-server-for10players";
            case 11:
                tourController.addAttributeFor11Model(resultTour, model);
                return "tour/tour-form-server-for11players";
            case 12:
                tourController.addAttributeFor12Model(resultTour, model);
                return "tour/tour-form-server-for12players";
            case 13:
                tourController.addAttributeFor13Model(resultTour, model);
                return "tour/tour-form-server-for13players";
            default:
                return "tour/tour-form";
        }


    }
}



