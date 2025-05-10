package com.nexapay.nexapay_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private String fullName;

    private String emailAddress;

    @JsonIgnore
    private String password;
}
