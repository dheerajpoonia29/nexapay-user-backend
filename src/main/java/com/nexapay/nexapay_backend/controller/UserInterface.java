package com.nexapay.nexapay_backend.controller;

import com.nexapay.dto.request.UserRequest;
import com.nexapay.dto.response.Response;
import com.nexapay.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserInterface {
    ResponseEntity<Response<Object>> apiHealth();

    ResponseEntity<Response<UserResponse>> loginUser(@RequestBody UserRequest userRequest);

    ResponseEntity<Response<UserResponse>> signupUser(@RequestBody UserRequest userRequest);

}
