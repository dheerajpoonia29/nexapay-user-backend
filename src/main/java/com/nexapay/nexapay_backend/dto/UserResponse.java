package com.nexapay.nexapay_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserResponse {
    private String name;

    private String email;

    @JsonIgnore
    private String password;

    private AccountResponse accountResponse;

    public UserResponse toResponse() {
        return UserResponse.builder()
                .name(this.name)
                .email(this.email)
                .build();
    }
}
