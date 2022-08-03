package com.example.tt_nsk.dto.mapper;

import com.example.tt_nsk.dto.UserDto;
import com.example.tt_nsk.entity.security.AccountUser;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;


@Mapper(componentModel = "spring")
public interface UserMapper {
    AccountUser toAccountUser(UserDto userDto);
    UserDto toUserDto(AccountUser accountUser);
}
