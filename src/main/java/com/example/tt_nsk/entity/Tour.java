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
//@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Entity
@Table (name = "tournament")
@EntityListeners(AuditingEntityListener.class)
public class Tour extends InfoEntity {
    @NotBlank
    @Column(name = "title")
    private String title;
    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "amount_players")
    private BigDecimal amountPlayers;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "cart_product",
//    joinColumns = @JoinColumn(name = "product_id"),
//    inverseJoinColumns = @JoinColumn(name = "cart_id"))
//    private Set<Cart> carts;

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
//    @Convert(converter = JsonForListPlayersDao.class)
    private String resultTour;


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
        return getId().equals(player.getId()) && title.equals(player.title)&& amountPlayers.equals(player.amountPlayers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),title, date,
                amountPlayers);
    }

//    @Builder
//    public Tour(Long id, String title, Address address, Date date,
//                Player player, BigDecimal amountPlayers, int version,
//                String createdBy, LocalDateTime createdDate, String lastModifiedBy,
//                LocalDateTime lastModifiedDate, List<TourImage> images, Status status) {
//        super(id, version, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
//        this.title = title;
//        this.date = date;
//        this.address = address;
//        this.amountPlayers = amountPlayers;
//        this.player = player;
//        this.images = images;
//        this.status = status;
//    }
}
