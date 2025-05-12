package com.nexapay.nexapay_backend.helper;

import com.nexapay.nexapay_backend.dto.UserRequest;
import com.nexapay.nexapay_backend.dto.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoginAuthentication {
    private static final Logger logger = LoggerFactory.getLogger(LoginAuthentication.class);

    public static boolean verifyPassword(UserRequest userRequest, UserResponse userResponse) {
        logger.info("verifying password");
        return userRequest.getPassword().equals(userResponse.getPassword());
    }
}
