package com.nexapay.nexapay_backend.controller;

import com.nexapay.nexapay_backend.dao.UserDAO;
import com.nexapay.nexapay_backend.dto.request.UserRequest;
import com.nexapay.nexapay_backend.dto.request.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class UserLoginController {
    @Autowired
    UserDAO userDAO;

    @GetMapping
    String health() {
        return "login api is up";
    }

    @GetMapping
    UserResponse login(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userDAO.readByEmail(userRequest.getEmailAddress());
        return userResponse;
    }
}
