package com.example.tt_nsk.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "upcoming_tournament_data")
@Getter
@NoArgsConstructor
public class UpcomingTournamentData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "tour_id")
    private long tourId;
    @Column(name = "total_players")
    private int totalPlayers;
    @Column(name = "reg_ends")
    private Timestamp registrationEnds;

    public UpcomingTournamentData(long tourId, int totalPlayers, Timestamp registrationEnds) {
        this.tourId = tourId;
        this.totalPlayers = totalPlayers;
        this.registrationEnds = registrationEnds;
    }

}
