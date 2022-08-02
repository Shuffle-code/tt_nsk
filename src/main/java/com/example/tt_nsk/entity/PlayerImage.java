package com.example.tt_nsk.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "player_image")
@Setter
@Getter
@NoArgsConstructor
public class PlayerImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "path")
    private String path;

    @Builder
    public PlayerImage(Long id, Player player, String path) {
        this.id = id;
        this.player = player;
        this.path = path;
    }
}
