package com.example.tt_nsk.aspects;

import com.example.tt_nsk.tournament.CurrentTournament;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TournamentAutoSave {

    @AfterReturning("execution(* com.example.tt_nsk.controller.PlayController.setScore(..))")
    public void autoSave(){
        CurrentTournament ct = CurrentTournament.getInstance();
        ct = null;
        System.out.println("Saved!!!!");
    }

}
