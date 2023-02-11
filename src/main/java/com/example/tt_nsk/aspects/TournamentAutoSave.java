package com.example.tt_nsk.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TournamentAutoSave {

    @AfterReturning("execution(* com.example.tt_nsk.controller.PlayController.setScore(..))")
    public void autoSave(){
        System.out.println("Saved!!!!");
    }

}
