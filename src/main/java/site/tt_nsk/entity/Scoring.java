package site.tt_nsk.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityListeners;
import javax.validation.constraints.Size;

@Component
@Setter
@Getter
@RequiredArgsConstructor
public class Scoring {
    private int placePlayer;
    private int indexPlayer;
    private double rating;
    private int set;
    private double delta;
    private int setWin;
    private int countWin;
    private int deltaWinLoss;
    private Long idPlayer;

    @Override
    public String toString() {
        return "Scoring{" +
                "placePlayer=" + placePlayer +
                ", indexPlayer=" + indexPlayer +
                ", rating=" + rating +
                ", set=" + set +
                ", delta=" + delta +
                ", setWin=" + setWin +
                ", countWin=" + countWin +
                ", deltaWinLoss=" + deltaWinLoss +
                ", idPlayer=" + idPlayer +
                '}';
    }
}

