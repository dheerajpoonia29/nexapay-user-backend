package com.nexapay.nexapay_backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {
    private String accountNo;

    private long balance;

    private int userId;

    public AccountResponse toResponse() {
        return AccountResponse.builder()
                .accountNo(this.accountNo)
                .balance(this.balance)
                .userId(this.userId)
                .build();
    }
}
