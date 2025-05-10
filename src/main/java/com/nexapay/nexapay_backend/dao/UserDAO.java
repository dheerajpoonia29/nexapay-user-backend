package com.nexapay.nexapay_backend.dao;

import com.nexapay.nexapay_backend.dto.request.UserRequest;
import com.nexapay.nexapay_backend.dto.request.UserResponse;
import com.nexapay.nexapay_backend.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
            return new UserResponse(userEntity.getFullName(), userRequest.getEmailAddress(), userEntity.getPassword());
        }
        logger.warn("user with email id {} is already present", userRequest.getEmailAddress());
        return null;
    }

    public UserResponse readByEmail(String email) {
        try {
            UserEntity userEntity = userRepository.findByEmail(email).get();
            return new UserResponse(userEntity.getFullName(), userEntity.getEmail(), userEntity.getPassword());
        } catch (NoSuchElementException e) {
            logger.warn("user not found");
        }
        return null;
    }
}
