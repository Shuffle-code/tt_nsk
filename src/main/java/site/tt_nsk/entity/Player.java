package site.tt_nsk.entity;

import site.tt_nsk.entity.common.InfoEntity;
import site.tt_nsk.entity.enums.Status;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table (name = "player")
@EntityListeners(AuditingEntityListener.class)
public class Player extends InfoEntity {
    @NotBlank
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "patronymic")
    private String patronymic;
    @NotBlank
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "rating")
    private BigDecimal rating;
    @Column(name = "rating_ttw")
    private BigDecimal ratingTtw;
    @Column(name = "year_of_birth")
    private Integer yearOfBirth;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "id_ttwr")
    private String idTtw;
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
}
