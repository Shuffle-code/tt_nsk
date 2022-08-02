package com.example.tt_nsk.dao.security;

import com.example.tt_nsk.entity.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityDao extends JpaRepository<Authority, Long> {
}
