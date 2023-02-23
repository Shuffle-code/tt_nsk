package com.example.tt_nsk.aspects;

import com.example.tt_nsk.dao.RegisteredPlayersRepo;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class UpdatePlayerStatuses {

//    private final RegisteredPlayersRepo registeredPlayersRepo;
//
//    @AfterReturning(value = "execution(* com.example.tt_nsk.controller.EnrollTournamentImpl_v2.enrollTournament(..))")
//    public void updateStatuses(){
//       List<Long>  ll = registeredPlayersRepo.findDistinctTourIds();
//    }
}
