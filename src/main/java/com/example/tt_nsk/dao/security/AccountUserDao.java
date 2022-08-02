package com.example.tt_nsk.dao.security;

import com.example.tt_nsk.entity.security.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountUserDao extends JpaRepository<AccountUser, Long> {
    Optional<AccountUser> findByUsername(String username);
}
