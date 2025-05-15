package com.nexapay.nexapay_backend.dao;

import com.nexapay.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    @Autowired
    UserRepository userRepository;

    public boolean saveUser(UserEntity userEntity) {
        logger.info("saving user");
        userRepository.save(userEntity);
        return true;
    }

    public UserEntity readUserByEmail(String email) {
        logger.info("find user");
        return userRepository.findByEmail(email).orElse(null);
    }
}
