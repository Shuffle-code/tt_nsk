package site.tt_nsk.entity.security;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "CONFIRMATION_CODE")
public class ConfirmationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code")
    private String code;
    @OneToOne(targetEntity = AccountUser.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "ID")
    private AccountUser accountUser;
    public boolean equals(String s) {
        if (this.code == s) return true;
        return code.equals(s);
    }
    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
