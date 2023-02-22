package com.example.tt_nsk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "registered_players")
@Getter
public class RegisteredPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "player_id")
    private long playerId;
    @Column(name = "tour_id")
    private long tourId;
    @Column(name = "status")
    private String status;

}
