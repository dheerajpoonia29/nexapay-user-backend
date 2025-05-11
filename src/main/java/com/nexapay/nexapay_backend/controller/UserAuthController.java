package com.nexapay.nexapay_backend.controller;

import com.nexapay.nexapay_backend.dao.UserDAO;
import com.nexapay.nexapay_backend.dto.UserRequest;
import com.nexapay.nexapay_backend.dto.UserResponse;
import com.nexapay.nexapay_backend.dto.Response;
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
    private static Logger logger = LoggerFactory.getLogger(UserAuthController.class);

    @Autowired
    UserDAO userDAO;

    @GetMapping("/health")
    ResponseEntity<Response> loginHealth() {
        logger.info("login health controller");
        return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                .responseStatus(HttpStatus.OK).responseMsg("user auth api is up!!").build());
    }

    @PostMapping("/login")
    ResponseEntity<Response> loginUser(@RequestBody UserRequest userRequest) {
        logger.info("login user controller");
        UserResponse userResponse = userDAO.readByEmail(userRequest.getEmail());
        if(userResponse==null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(
                    Response.builder().responseStatus(HttpStatus.UNAUTHORIZED)
                            .responseStatusInt(HttpStatus.UNAUTHORIZED.value())
                            .responseMsg("user not found")
                            .userResponse(null).build()
            );
        }
        // todo check password, helper -> authentication
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                Response.builder().responseStatus(HttpStatus.OK)
                        .responseStatusInt(HttpStatus.OK.value())
                        .responseMsg("user found")
                        .userResponse(userResponse).build()
        );
    }

    @PostMapping("/signup")
    ResponseEntity<Response> signupUser(@RequestBody UserRequest userRequest) {
        logger.info("signup user controller");
        UserResponse userResponse = userDAO.createUser(userRequest);
        if(userResponse==null) {
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(
                    Response.builder().responseStatus(HttpStatus.CONFLICT)
                            .responseStatusInt(HttpStatus.CONFLICT.value())
                            .responseMsg("user already present")
                            .userResponse(null).build()
            );
        }
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(
                Response.builder().responseStatus(HttpStatus.CREATED)
                        .responseStatusInt(HttpStatus.CREATED.value())
                        .responseMsg("user created")
                        .userResponse(userResponse).build()
        );
    }
}
