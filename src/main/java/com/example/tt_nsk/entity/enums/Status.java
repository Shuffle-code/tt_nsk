package com.example.tt_nsk.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    ACTIVE("Участник следующего турнира"), DISABLE("Регулярный участник турниров"), NOT_ACTIVE("Почетный гость турнира"), DELETED("Покинул чат");
//    ACTIVE("Участник следующего турниров"), DISABLE("Регулярный участник турнира");
    private final String title;
}
