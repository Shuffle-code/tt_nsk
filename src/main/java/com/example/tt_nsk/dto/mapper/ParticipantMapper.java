package com.example.tt_nsk.dto.mapper;

import com.example.tt_nsk.dto.UserDto;
import com.example.tt_nsk.entity.Player;
import com.example.tt_nsk.entity.security.AccountUser;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ParticipantMapper {
    AccountUser toAccountUser(Player player);
    Player toPlayer(AccountUser accountUser);
}
