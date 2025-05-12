package com.nexapay.nexapay_backend.controller;

import com.nexapay.nexapay_backend.dto.UserRequest;
import com.nexapay.nexapay_backend.dto.Response;
import com.nexapay.nexapay_backend.dto.UserResponse;
import com.nexapay.nexapay_backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserAuthController {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthController.class);

    @Autowired
    UserService userService;

    @GetMapping("/health")
    ResponseEntity<Response> loginHealth() {
        logger.info("auth api");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .responseStatus(HttpStatus.OK)
                        .responseMsg("user auth api is up!!")
                        .responseData(null).build());
    }

    @PostMapping("/login")
    ResponseEntity<Response<UserResponse>> loginUser(@RequestBody UserRequest userRequest) {
        logger.info("login user");
        Response<UserResponse> response = userService.getUserByEmailAndAuthenticate(userRequest);
        return ResponseEntity.status(response.getResponseStatus()).body(response);
    }

    @PostMapping("/signup")
    ResponseEntity<Response<UserResponse>> signupUser(@RequestBody UserRequest userRequest) {
        logger.info("signup user");
        Response<UserResponse> response = userService.createUserAndPersist(userRequest);
        return ResponseEntity.status(response.getResponseStatus()).body(response);
    }
}
