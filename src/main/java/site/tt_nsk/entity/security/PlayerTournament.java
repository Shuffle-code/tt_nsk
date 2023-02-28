package site.tt_nsk.entity.security;

import site.tt_nsk.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "player_tournament")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlayerTournament extends BaseEntity {

    @Column(name = "player_id")
    private Long playerId;

    @Column(name = "tournament_id")
    private Long tournamentId;
}
