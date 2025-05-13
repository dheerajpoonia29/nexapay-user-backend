package com.nexapay.nexapay_backend.model;

import com.nexapay.dto.response.AccountResponse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String accountNo;

    @Column(nullable = false)
    private long balance;

    // todo add bank name column

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private UserEntity userEntity;
}
