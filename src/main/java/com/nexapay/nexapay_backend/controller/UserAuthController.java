package com.nexapay.nexapay_backend.controller;

import com.nexapay.nexapay_backend.dao.UserDAO;
import com.nexapay.nexapay_backend.dto.request.UserRequest;
import com.nexapay.nexapay_backend.dto.request.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    @Autowired
    UserDAO userDAO;

    @GetMapping("/health")
    String loginHealth() {
        return "user auth api is up!!";
    }

    @GetMapping("/login")
    UserResponse loginUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userDAO.readByEmail(userRequest.getEmailAddress());
        return userResponse;
    }

    @PostMapping("/signup")
    UserResponse signupUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userDAO.createUser(userRequest);
        return userResponse;
    }
}
