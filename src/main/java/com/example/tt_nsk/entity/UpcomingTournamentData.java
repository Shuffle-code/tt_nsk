package com.example.tt_nsk.entity;

import lombok.Getter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "upcoming_tournament_data")
@Getter
public class UpcomingTournamentData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "tour_id")
    private long tourId;
    @Column(name = "total_players")
    private int totalPlayers;
    @Column(name = "reg_ends")
    private Timestamp registrationEnds;

//    @OneToOne(targetEntity = Tour.class, fetch = FetchType.EAGER)
//    @JoinColumn(name = "tout_id", referencedColumnName = "id")

    @OneToOne(targetEntity = Tour.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_id", referencedColumnName = "id", insertable=false, updatable=false)
    private Tour tournament;

    public void setTournament(Tour tournament) {
        this.tournament = tournament;
    }

}
