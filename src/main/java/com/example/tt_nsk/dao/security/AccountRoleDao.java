package com.example.tt_nsk.dao.security;

import com.example.tt_nsk.entity.security.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRoleDao extends JpaRepository<AccountRole, Long> {
    AccountRole findByName(String name);
}
