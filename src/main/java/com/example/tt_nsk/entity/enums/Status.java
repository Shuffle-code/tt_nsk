package com.example.tt_nsk.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    ACTIVE("Частый участник турниров"), DISABLE("Редкий гость"), ;

    private final String title;
}
