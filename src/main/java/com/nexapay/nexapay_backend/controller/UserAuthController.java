package com.nexapay.nexapay_backend.controller;

import com.nexapay.nexapay_backend.dao.UserDAO;
import com.nexapay.nexapay_backend.dto.request.UserRequest;
import com.nexapay.nexapay_backend.dto.request.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    @Autowired
    UserDAO userDAO;

    @GetMapping("/health")
    ResponseEntity<UserResponse> loginHealth() {
        return ResponseEntity.status(HttpStatus.OK).body(UserResponse.builder().responseStatus(HttpStatus.OK).responseMsg("user auth api is up!!").build());
    }

    @GetMapping("/login")
    ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userDAO.readByEmail(userRequest.getEmailAddress());
        userResponse.setResponseStatusInt(userResponse.getResponseStatus().value());
        return ResponseEntity.status(userResponse.getResponseStatusInt()).body(userResponse);
    }

    @PostMapping("/signup")
    ResponseEntity<UserResponse> signupUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userDAO.createUser(userRequest);
        userResponse.setResponseStatusInt(userResponse.getResponseStatus().value());
        return ResponseEntity.status(userResponse.getResponseStatusInt()).body(userResponse);
    }
}
