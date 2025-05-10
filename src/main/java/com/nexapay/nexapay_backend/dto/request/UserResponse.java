package com.nexapay.nexapay_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private String fullName;

    private String emailAddress;

    private String password;
}
