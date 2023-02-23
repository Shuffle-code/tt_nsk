package com.example.tt_nsk.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "registered_players")
@Getter
@NoArgsConstructor
public class RegisteredPlayer {

//    public enum Status {
//        RESERVED("RESERVED"), REGISTERED("REGISTERED");
//
//        private String code;
//
//        private Status  (String code) {
//            this.code = code;
//        }
//
//        public String getCode() {
//            return code;
//        }
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "player_id")
    private long playerId;
    @Column(name = "tour_id")
    private long tourId;
    @Column(name = "status")
    //@Enumerated(EnumType.STRING)
    //@GeneratedValue(strategy = GenerationType.AUTO, generator = "reg_status")
    private String status;

    public RegisteredPlayer(long playerId, long tourId, String status) {
        this.playerId = playerId;
        this.tourId = tourId;
        this.status = status;
    }

    public RegisteredPlayer(Long playerId, Long tourId) {
        this.playerId = playerId;
        this.tourId = tourId;
    }
}
