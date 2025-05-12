package com.nexapay.nexapay_backend.dao;

import com.nexapay.nexapay_backend.dto.UserRequest;
import com.nexapay.nexapay_backend.dto.UserResponse;
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
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    @Autowired
    UserRepository userRepository;

    public boolean createUser(UserEntity userEntity) {
        logger.info("saving user");
        userRepository.save(userEntity);
        return true;
    }

    public UserEntity readByEmail(String email) {
        logger.info("find user");
        return userRepository.findByEmail(email).orElse(null);
    }
}
