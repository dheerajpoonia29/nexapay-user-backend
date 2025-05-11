package com.nexapay.nexapay_backend.dao;

import com.nexapay.nexapay_backend.dto.request.UserRequest;
import com.nexapay.nexapay_backend.dto.request.UserResponse;
import com.nexapay.nexapay_backend.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Random;

@Component
public class UserDAO {
    private static Logger logger = LoggerFactory.getLogger(UserDAO.class);

    @Autowired
    UserRepository userRepository;

    public UserResponse createUser(UserRequest userRequest) {
        if(readByEmail(userRequest.getEmailAddress())==null) {
            UserEntity userEntity = new UserEntity(new Random().nextInt(1000), userRequest.getFullName(),
                    userRequest.getEmailAddress(), userRequest.getPassword());
            userRepository.save(userEntity);
            return UserResponse.builder().responseStatus(HttpStatus.CREATED).responseMsg("user created")
                    .fullName(userEntity.getFullName())
                    .emailAddress(userEntity.getEmail()).build();
        }
        logger.warn("user with email id {} is already present", userRequest.getEmailAddress());
        return UserResponse.builder().responseStatus(HttpStatus.FORBIDDEN).responseMsg("user already exist").build();
    }

    public UserResponse readByEmail(String email) {
        try {
            UserEntity userEntity = userRepository.findByEmail(email).get();
            if(userEntity.getEmail()!=null) {
                return UserResponse.builder().responseStatus(HttpStatus.FOUND).responseMsg("user found")
                        .fullName(userEntity.getFullName())
                        .emailAddress(userEntity.getEmail()).build();
            }
        } catch (NoSuchElementException e) {
            logger.warn("user not found");
        }
        return UserResponse.builder().responseStatus(HttpStatus.FOUND).responseMsg("user not found").build();
    }
}
