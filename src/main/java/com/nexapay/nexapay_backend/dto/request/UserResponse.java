package com.nexapay.nexapay_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
public class UserResponse {
    @JsonIgnore
    private HttpStatus responseStatus;

    private int responseStatusInt;

    private String responseMsg;

    private String fullName;

    private String emailAddress;

    @JsonIgnore
    private String password;
}
