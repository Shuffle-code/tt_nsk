package site.tt_nsk.dto.mapper;

import site.tt_nsk.dto.UserDto;
import site.tt_nsk.entity.security.AccountUser;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    AccountUser toAccountUser(UserDto userDto);
    UserDto toUserDto(AccountUser accountUser);
}
