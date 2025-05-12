package com.nexapay.nexapay_backend.service;

import com.nexapay.nexapay_backend.controller.UserAuthController;
import com.nexapay.nexapay_backend.dao.UserDAO;
import com.nexapay.nexapay_backend.dto.Response;
import com.nexapay.nexapay_backend.dto.UserRequest;
import com.nexapay.nexapay_backend.dto.UserResponse;
import com.nexapay.nexapay_backend.helper.LoginAuthentication;
import com.nexapay.nexapay_backend.model.UserEntity;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserAuthService {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthService.class);

    @Autowired
    UserDAO userDAO;

    public Response getUserByEmailAndAuthenticate(UserRequest userRequest) {
        logger.info("read user by email");
        UserEntity userEntity = userDAO.readByEmail(userRequest.getEmail());
        if(userEntity!=null) {
            logger.info("authenticate user");
            if (LoginAuthentication.verifyPassword(userRequest.getPassword(), userEntity.getPassword())) {
                return Response.builder().responseStatus(HttpStatus.OK).responseStatusInt(HttpStatus.OK.value()).responseMsg("user authenticated")
                        .userResponse(UserResponse.builder().email(userRequest.getEmail()).name(userRequest.getName()).build())
                        .build();
            }
        }
        return Response.builder().responseStatus(HttpStatus.UNAUTHORIZED).responseStatusInt(HttpStatus.UNAUTHORIZED.value()).responseMsg("user not found or not authenticated")
                .userResponse(null)
                .build();
    }

    public Response createUserAndPersist(UserRequest userRequest) {
        logger.info("check user not exist");
        if(userDAO.readByEmail(userRequest.getEmail())!=null) {
            return Response.builder().responseStatus(HttpStatus.CONFLICT)
                            .responseStatusInt(HttpStatus.CONFLICT.value())
                            .responseMsg("user already present")
                            .userResponse(null).build();
        }
        logger.info("create user");
        UserEntity userEntity = new UserEntity(new Random().nextInt(1000), userRequest.getName(),
                userRequest.getEmail(), userRequest.getPassword());
        logger.info("persist user");
        boolean status = userDAO.createUser(userEntity);
        if (status) {
            return Response.builder().responseStatus(HttpStatus.CREATED)
                    .responseStatusInt(HttpStatus.CREATED.value())
                    .responseMsg("user created")
                    .userResponse(UserResponse.builder().name(userEntity.getName()).email(userEntity.getEmail())
                            .build()).build();
        }
        return Response.builder().responseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .responseStatusInt(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .responseMsg("something went wrong")
                .userResponse(null).build();
    }
}
