package com.example.tt_nsk.dao;

import com.example.tt_nsk.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDao extends JpaRepository<Address, Long> {
}
