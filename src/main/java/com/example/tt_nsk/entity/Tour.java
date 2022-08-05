package com.example.tt_nsk.entity;

import com.example.tt_nsk.entity.common.InfoEntity;
import com.example.tt_nsk.entity.security.AccountUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
//@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Entity
@Table (name = "tournament")
@EntityListeners(AuditingEntityListener.class)
public class Tour extends InfoEntity {
    @Column(name = "title")
    private String title;
    @Column(name = "date")
    private String date;
    @Column(name = "address_id")
    private String addressId;
    @Column(name = "amount_players")
    private BigDecimal amountPlayers;
//    @Column(name = "winner_id")
//    private String winnerId;



//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "cart_product",
//    joinColumns = @JoinColumn(name = "product_id"),
//    inverseJoinColumns = @JoinColumn(name = "cart_id"))
//    private Set<Cart> carts;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "tour")
    private List<TourImage> images;

    @OneToOne(targetEntity = Player.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "winner_id", referencedColumnName = "ID")
    private Player player;


    public void addImage(TourImage tourImage) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(tourImage);
    }

//    @Override
//    public String toString() {
//        return "tour{" +
//                "title='" + title + '\'' +
//                ", date='" + date + '\'' +
//                ", addressId='" + addressId + '\'' +
//                ", amountPlayers=" + amountPlayers +
//                ", winnerId=" + winnerId +
//                ", images=" + images +
//                '}';
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour player = (Tour) o;
        return getId().equals(player.getId()) && title.equals(player.title) && addressId.equals(player.addressId)&& amountPlayers.equals(player.amountPlayers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),title, date,addressId,
                amountPlayers);
    }

//    @Builder
//    public Tour(Long id, String title, String addressId, String date,
//                String winnerId, BigDecimal amountPlayers, int version,
//                String createdBy, LocalDateTime createdDate, String lastModifiedBy,
//                LocalDateTime lastModifiedDate, List<TourImage> images) {
//        super(id, version, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
//        this.title = title;
//        this.date = date;
//        this.addressId = addressId;
//        this.amountPlayers = amountPlayers;
//        this.winnerId = winnerId;
//        this.images = images;
//    }
}
