package com.nexapay.nexapay_backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountRequest {
    private UserRequest userRequest;

    private String accountNo;

    private long balance;
}
