package com.example.tt_nsk.entity;

import com.example.tt_nsk.entity.common.InfoEntity;
import com.example.tt_nsk.entity.enums.TourStatus;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table (name = "tournament")
@EntityListeners(AuditingEntityListener.class)
public class Tour extends InfoEntity {

    @Id
    @Column(name = "id")
    Long id;

    @NotBlank
    @Column(name = "title")
    private String title;
    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "amount_players")
    private BigDecimal amountPlayers;

    @OneToOne(targetEntity = Address.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", referencedColumnName = "ID")
    private Address address;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "tour")
    private List<TourImage> images;

    public void addImage(TourImage tourImage) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(tourImage);
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TourStatus status;

    @OneToOne(targetEntity = Player.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "winner_id", referencedColumnName = "ID")
    private Player player;

    @Column(name = "result_tour")
    private String resultTour;

    @Column(name = "scoring")
    private String scoring;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour player = (Tour) o;
        return getId().equals(player.getId()) && title.equals(player.title)&& amountPlayers.equals(player.amountPlayers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),title, date,
                amountPlayers);
    }
}
