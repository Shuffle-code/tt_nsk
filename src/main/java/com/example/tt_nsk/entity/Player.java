package com.example.tt_nsk.entity;

import com.example.tt_nsk.entity.common.InfoEntity;
import com.example.tt_nsk.entity.enums.Status;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
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
@Table (name = "player")
@EntityListeners(AuditingEntityListener.class)
public class Player extends InfoEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "rating")
    private BigDecimal rating;
    @Column(name = "rating_ttw")
    private BigDecimal ratingTtw;
    @Column(name = "year_of_birth")
    private Integer yearOfBirth;
//    @Column(yearOfBirthname = "manufacture_date")
//    private LocalDate manufactureDate;
//    @ManyToOne
//    @JoinColumn(name = "manufacturer_id")
//    private Manufacturer manufacturer;
//    @Version
//    @Column(name = "VERSION")
//    private int version;
//    @CreatedBy
//    @Column(name = "CREATED_BY")
//    private String createdBy;
//    @CreatedDate
//    @Column(name = "CREATED_DATE")
//    private LocalDateTime createdDate;
//    @LastModifiedBy
//    @Column(name = "LAST_MODIFIED_BY")
//    private String lastModifiedBy;
//    @LastModifiedDate
//    @Column(name = "LAST_MODIFIED_DATE")
//    private LocalDateTime lastModifiedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "cart_product",
//    joinColumns = @JoinColumn(name = "product_id"),
//    inverseJoinColumns = @JoinColumn(name = "cart_id"))
//    private Set<Cart> carts;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "player")
    private List<PlayerImage> images;

    public void addImage(PlayerImage playerImage) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(playerImage);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + getId() +
                ", firstname ='" + firstname + '\'' +
                ", patronymic =" + patronymic +
                ", lastname =" + lastname +
                ", rating =" + rating +
                ", ratingTtw =" + ratingTtw +
                ", yearOfBirth =" + yearOfBirth +
//                ", manufacturer=" + manufacturer.getName() +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return getId().equals(player.getId()) && firstname.equals(player.firstname) && patronymic.equals(player.patronymic) && lastname.equals(player.lastname) && rating.equals(player.rating)&& ratingTtw.equals(player.ratingTtw)&& yearOfBirth.equals(player.yearOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), firstname, patronymic, lastname, rating,
                ratingTtw, yearOfBirth);
    }

    @Builder
    public Player(Long id, String firstname, String patronymic, String lastname,
                  BigDecimal rating, BigDecimal ratingTtw, Integer yearOfBirth, int version,
                  String createdBy, LocalDateTime createdDate, String lastModifiedBy,
                  LocalDateTime lastModifiedDate, Status status, List<PlayerImage> images) {
        super(id, version, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
//        this.id = id;
        this.firstname = firstname;
        this.patronymic = patronymic;
        this.lastname = lastname;
        this.rating = rating;
        this.ratingTtw = ratingTtw;
        this.yearOfBirth = yearOfBirth;
//        this.version = version;
//        this.createdBy = createdBy;
//        this.createdDate = createdDate;
//        this.lastModifiedBy = lastModifiedBy;
//        this.lastModifiedDate = lastModifiedDate;
        this.status = status;
        this.images = images;
    }
}
