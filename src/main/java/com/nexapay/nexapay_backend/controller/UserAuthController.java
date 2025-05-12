package com.nexapay.nexapay_backend.controller;

import com.nexapay.nexapay_backend.dao.UserDAO;
import com.nexapay.nexapay_backend.dto.UserRequest;
import com.nexapay.nexapay_backend.dto.UserResponse;
import com.nexapay.nexapay_backend.dto.Response;
import com.nexapay.nexapay_backend.helper.LoginAuthentication;
import com.nexapay.nexapay_backend.model.UserEntity;
import com.nexapay.nexapay_backend.service.UserAuthService;
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
    UserDAO userDAO;

    @Autowired
    UserAuthService userAuthService;

    @GetMapping("/health")
    ResponseEntity<Response> loginHealth() {
        logger.info("login health controller");
        return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                .responseStatus(HttpStatus.OK).responseMsg("user auth api is up!!").build());
    }

    @PostMapping("/login")
    ResponseEntity<Response> loginUser(@RequestBody UserRequest userRequest) {
        logger.info("login user controller");
        Response response = userAuthService.getUserByEmailAndAuthenticate(userRequest);
        return ResponseEntity.status(response.getResponseStatus()).body(response);
    }

    @PostMapping("/signup")
    ResponseEntity<Response> signupUser(@RequestBody UserRequest userRequest) {
        logger.info("signup user controller");
        Response response = userAuthService.createUserAndPersist(userRequest);
        return ResponseEntity.status(response.getResponseStatus()).body(response);
    }
}
