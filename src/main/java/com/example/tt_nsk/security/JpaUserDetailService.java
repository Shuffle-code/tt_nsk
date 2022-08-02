package com.example.tt_nsk.security;

import com.example.tt_nsk.dao.security.AccountUserDao;
import com.example.tt_nsk.dao.security.ConfirmationCodeDao;
import com.example.tt_nsk.entity.security.AccountUser;
import com.example.tt_nsk.entity.security.ConfirmationCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JpaUserDetailService implements UserDetailsService {

    private final AccountUserDao accountUserDao;
//    private final ConfirmationCodeDao confirmationCodeDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountUser accountUser = accountUserDao.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username: " + username + " not found")
        );
//        String code = getConfirmationCode();
//        generateConfirmationCode(username, code);
//        System.out.println(code);
        return accountUser;
    }


    public String getConfirmationCode() {
        String confirmationCode;
        return confirmationCode = RandomStringUtils.randomAscii(8);
    }

//    public void generateConfirmationCode(String username, String code) {
//        AccountUser accountUser = accountUserDao.findByUsername(username).get();
//        ConfirmationCode confirmationCode = ConfirmationCode.builder().
//                confirmationCode(code)
//                .accountUser(accountUser)
//                .build();
//        confirmationCodeDao.save(confirmationCode);
//    }
}
