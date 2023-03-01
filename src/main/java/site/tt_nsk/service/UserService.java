package site.tt_nsk.service;

import site.tt_nsk.dto.UserDto;
import site.tt_nsk.entity.security.AccountUser;

import java.io.IOException;
import java.util.List;

public interface UserService {

    UserDto register(UserDto userDto) throws IOException;
    UserDto update(UserDto userDto);
    AccountUser findByUsername(String username);
    AccountUser update(AccountUser userDto);
    UserDto findById(Long id);
    List<UserDto> findAll();
    String getConfirmationCode();
    void deleteById(Long id);
    void generateConfirmationCode(UserDto thisUser, String confirmationCode);
}
