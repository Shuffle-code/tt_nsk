package com.example.tt_nsk.service;

import com.example.tt_nsk.dao.AddressDao;
import com.example.tt_nsk.entity.Address;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {
    private final AddressDao addressDao;

    public List<Address> findAll(){
        return addressDao.findAll();
    }
}
