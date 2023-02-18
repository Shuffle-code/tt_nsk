package com.example.tt_nsk.entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class Pair<P, P1> {
    Player player1;
    Player player2;
}