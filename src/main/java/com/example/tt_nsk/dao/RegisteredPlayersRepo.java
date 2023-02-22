package com.example.tt_nsk.dao;

import com.example.tt_nsk.entity.RegisteredPlayer;
import org.springframework.data.repository.CrudRepository;

public interface RegisteredPlayersRepo extends CrudRepository<RegisteredPlayer, Long> {
}
