package site.tt_nsk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.tt_nsk.dto.CurrentTournament;
import site.tt_nsk.dto.PlayerBriefRepresentationDto;
import site.tt_nsk.service.PlayService;
import site.tt_nsk.service.TourService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/video")
public class VideoController {
    private final PlayService playService;
    private final PlayController playController;
    private final TourService tourService;
    @GetMapping
    public String rules(Model model, HttpSession httpSession) {
        Long currentTour = tourService.getCurrentTourForTranslation();
//        currentTour = currentTour != null? currentTour : tourService.getTourForTranslation();
        httpSession.setAttribute("tourId", currentTour);
        List<PlayerBriefRepresentationDto> playerBriefRepresentationDtoListSortedByRatingDesc = playController.getAllRegisteredPlayers(currentTour);
        List<List<String>> results = playService.compileResultTable(playerBriefRepresentationDtoListSortedByRatingDesc);
        CurrentTournament ct = CurrentTournament.builder()
                .players(playerBriefRepresentationDtoListSortedByRatingDesc)
                .resultTable(results)
                .build();
        model.addAttribute("score", ct);
        return "video/video";
    }
    
}




