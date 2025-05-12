package com.nexapay.nexapay_backend.model;

import com.nexapay.nexapay_backend.dto.AccountResponse;
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

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private UserEntity userEntity;

    public AccountResponse toResponse() {
        return AccountResponse.builder()
                .accountNo(this.accountNo)
                .balance(this.balance)
//                .userResponse(this.userEntity != null ? this.userEntity.toResponse() : null)
                .userId(this.userEntity != null ? this.userEntity.getId() : null)
                .build();
    }
}
