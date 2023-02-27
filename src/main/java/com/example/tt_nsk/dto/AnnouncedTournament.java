package com.example.tt_nsk.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AnnouncedTournament {
    @Schema(name = "tourId", description = "ID турнира в таблице tournament", example = "3")
    private final Long tourId;
    @Schema(name = "totalPlayers", description = "Максимальное количество игроков, которые могут принять участие в турнире", example = "2")
    private final Integer totalPlayers;
    @Schema(name = "registrationEndsStr", description = "Дата и время, когда регистрация на турнир будет закрыта", example = "2023-02-27 16:27:18.153")
    private final String registrationEndsStr;
}
