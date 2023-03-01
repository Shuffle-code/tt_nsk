package site.tt_nsk.entity;

import site.tt_nsk.entity.common.InfoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.math.BigDecimal;
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "address")
@EntityListeners(AuditingEntityListener.class)
public class Address extends InfoEntity {
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "house")
    private BigDecimal house;
}
