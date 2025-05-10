package com.nexapay.nexapay_backend.controller;

import com.nexapay.nexapay_backend.dao.UserDAO;
import com.nexapay.nexapay_backend.dao.UserRepository;
import com.nexapay.nexapay_backend.dto.request.UserRequest;
import com.nexapay.nexapay_backend.dto.request.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
public class UserSignupController {
    @Autowired
    UserDAO userDAO;

    @GetMapping
    String health() {
        return "signup api is up";
    }

    @PostMapping
    UserResponse createUser(@RequestBody UserRequest userRequest) {
        return userDAO.createUser(userRequest);
    }
}
