package com.nexapay.nexapay_backend.service;

import com.nexapay.dto.request.UserRequest;
import com.nexapay.dto.response.AccountResponse;
import com.nexapay.nexapay_backend.dao.UserDAO;
import com.nexapay.dto.response.Response;
import com.nexapay.dto.response.UserResponse;
import com.nexapay.nexapay_backend.helper.LoginAuthentication;
import com.nexapay.nexapay_backend.model.AccountEntity;
import com.nexapay.nexapay_backend.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.nexapay.nexapay_backend.helper.CreateUserResponse.createResponse;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserDAO userDAO;

    public Response<UserResponse> getUserByEmailAndAuthenticate(UserRequest userRequest) {
        logger.info("read user by email");
        UserEntity userEntity = userDAO.readUserByEmail(userRequest.getEmail());
        if (userEntity != null) {
            logger.info("authenticate user");
            if (LoginAuthentication.verifyPassword(userRequest.getPassword(), userEntity.getPassword())) {
                return createResponse(HttpStatus.OK, "user authenticated", userEntity);
            }
        }
        return createResponse(HttpStatus.UNAUTHORIZED, "user not found or not authenticated", null);
    }

    public Response<UserResponse> createUserAndPersist(UserRequest userRequest) {
        logger.info("check user not exist");
        if (userDAO.readUserByEmail(userRequest.getEmail()) != null) {
            return createResponse(HttpStatus.CONFLICT, "user already present", null);
        }

        logger.info("create user");
        UserEntity userEntity = UserEntity.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword()).build();

        logger.info("persist user");
        boolean status = userDAO.saveUser(userEntity);
        if (status) {
            return createResponse(HttpStatus.CREATED, "user created", userEntity);
        }

        logger.info("return response");
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, "something went wrong", null);
    }
}
