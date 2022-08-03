package com.example.tt_nsk.service;

import com.example.tt_nsk.dto.UserDto;
import com.example.tt_nsk.entity.security.AccountUser;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Artem Kropotov
 * created at 01.06.2022
 **/

public interface UserService {

    UserDto register(UserDto userDto);
    UserDto update(UserDto userDto);
    AccountUser findByUsername(String username);
    AccountUser update(AccountUser userDto);
    UserDto findById(Long id);
    List<UserDto> findAll();
    String getConfirmationCode();
    void deleteById(Long id);
    void generateConfirmationCode(UserDto thisUser, String confirmationCode);
}
