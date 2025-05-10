package com.nexapay.nexapay_backend.dto.request;

import lombok.Data;

@Data
public class UserRequest {
    private String fullName;

    private String emailAddress;

    private String password;
}
