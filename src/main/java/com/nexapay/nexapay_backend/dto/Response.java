package com.nexapay.nexapay_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    @JsonIgnore
    private HttpStatus responseStatus;

    private int responseStatusInt;

    private String responseMsg;

    private UserResponse userResponse;
}
