package com.example.tt_nsk.entity.security;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
//@RequiredArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "CONFIRMATION_CODE")
public class ConfirmationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code")
    private String confirmationCode;
    @OneToOne(targetEntity = AccountUser.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "ID")
    private AccountUser accountUser;
}
