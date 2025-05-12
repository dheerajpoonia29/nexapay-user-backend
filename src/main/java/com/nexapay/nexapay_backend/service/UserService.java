package com.nexapay.nexapay_backend.service;

import com.nexapay.nexapay_backend.dao.UserDAO;
import com.nexapay.nexapay_backend.dto.Response;
import com.nexapay.nexapay_backend.dto.UserRequest;
import com.nexapay.nexapay_backend.dto.UserResponse;
import com.nexapay.nexapay_backend.helper.LoginAuthentication;
import com.nexapay.nexapay_backend.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
                return Response.<UserResponse>builder()
                        .responseStatus(HttpStatus.OK)
                        .responseStatusInt(HttpStatus.OK.value())
                        .responseMsg("user authenticated")
                        .responseData(userEntity.toResponse())
                        .build();
            }
        }
        return Response.<UserResponse>builder()
                .responseStatus(HttpStatus.UNAUTHORIZED)
                .responseStatusInt(HttpStatus.UNAUTHORIZED.value())
                .responseMsg("user not found or not authenticated")
                .responseData(null)
                .build();
    }

    public Response<UserResponse> createUserAndPersist(UserRequest userRequest) {
        logger.info("check user not exist");
        if (userDAO.readUserByEmail(userRequest.getEmail()) != null) {
            return Response.<UserResponse>builder()
                    .responseStatus(HttpStatus.CONFLICT)
                    .responseStatusInt(HttpStatus.CONFLICT.value())
                    .responseMsg("user already present")
                    .responseData(null).build();
        }

        logger.info("create user");
        UserEntity userEntity = UserEntity.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword()).build();

        logger.info("persist user");
        boolean status = userDAO.saveUser(userEntity);
        if (status) {
            return Response.<UserResponse>builder()
                    .responseStatus(HttpStatus.CREATED)
                    .responseStatusInt(HttpStatus.CREATED.value())
                    .responseMsg("user created")
                    .responseData(userEntity.toResponse()).build();
        }

        logger.info("return response");
        return Response.<UserResponse>builder().responseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .responseStatusInt(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .responseMsg("something went wrong")
                .responseData(null).build();
    }
}
