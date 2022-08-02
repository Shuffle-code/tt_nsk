package com.example.tt_nsk.dao.security;

import com.example.tt_nsk.entity.security.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationCodeDao extends JpaRepository<ConfirmationCode, Long> {
    ConfirmationCode findConfirmationCodeByAccountUser_Id (Long id);
}
