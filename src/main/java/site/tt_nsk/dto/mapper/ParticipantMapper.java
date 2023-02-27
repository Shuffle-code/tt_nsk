package site.tt_nsk.dto.mapper;

import site.tt_nsk.entity.Player;
import site.tt_nsk.entity.security.AccountUser;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ParticipantMapper {
    AccountUser toAccountUser(Player player);
    Player toPlayer(AccountUser accountUser);
}
