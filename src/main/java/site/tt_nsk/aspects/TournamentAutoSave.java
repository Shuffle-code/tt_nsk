package site.tt_nsk.aspects;

import site.tt_nsk.dao.TourDao;
import site.tt_nsk.service.PlayService;
import site.tt_nsk.tournament.CurrentTournament;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
@Aspect
@Component
@RequiredArgsConstructor
public class TournamentAutoSave {
    private final PlayService playService;
    private final TourDao tourDao;
    @AfterReturning("execution(* site.tt_nsk.controller.RefereeController.setScore(..))")
    public void autoSave(){
        String toBeSaved = playService.createCurrentTournamentState(CurrentTournament.getInstance().tournamentData());
        tourDao.updateCurrentTournamentById(toBeSaved, CurrentTournament.getInstance().tournamentData().getTuornamentId());
        System.out.println("Saved: " + CurrentTournament.getInstance().tournamentData());
    }
}
